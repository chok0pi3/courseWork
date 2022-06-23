package com.company;
import java.util.Objects;
import java.util.Scanner;

public class Graph {
    private int[][] adjMatrix; // основна матриця суміжності
    private final int[][] inputAdjMatrix; // матриця суміжності, яка утворюється при зчитуванні з файлу початкових даних
    private int vertSize; // кількість вершин графа
    private final List<Vertex> vertexList = new List<>(); //зберігаємо вершини графа

    public Graph(int size){
        inputAdjMatrix = new int[size][size];
        vertSize=0;
    }

    public void setAdjMatrix(int[][] matrix){ // встановлення основної матриці суміжності
        adjMatrix = new int[matrix.length][matrix.length];
        for(int i=0; i< matrix.length;i++){
            for(int j=0; j<matrix.length; j++){
                adjMatrix[i][j] = matrix[i][j];
            }
        }
    }
    public void initialize(){ // встановлення вхідної матриці як основної
        setAdjMatrix(inputAdjMatrix);
    }

    public void addVertex(String label) { // метод для додавання нових вершин
        vertexList.add(new Vertex(label));
        vertSize++;
    }

    public void addEdge(int start, int end, int weight){ // метод додавання ребер, з їхньою вагою
        inputAdjMatrix[start][end]=weight;
        inputAdjMatrix[end][start]=weight;
    }

    public int check(int pos){ // Метод повертає індекс суміжної вершини, якщо вона не відвідана
        for(int i=0; i< vertSize; i++){
            if(adjMatrix[pos][i] != 0 & !vertexList.getNode(i).v.isVisited)
                return i;
        }
        return -1; // якщо така вершина не знайдена, значення індексу -1 свідчить про неіснування такої вершини
    }

    public void passInDeep(String toFind){
        Stack stack = new Stack();
        List<String> way = new List<>();
        int index = 0;
        int endIndex = getIndexByName(toFind);
        way.add(vertexList.getNode(index).v.name);
        vertexList.getNode(index).v.isVisited = true; // та одразу ж вказуємо, що вона пройдена
        stack.push(index); // заносимо початкову вершину в стек

        while(!stack.isEmpty()){ // якщо стек пустий, це свідчить про те, що всі вершини пройдені
            int incident = check(stack.peek()); // далі вершини заносяться в стек, поки граф має їм інцидентні
            if(incident == -1) incident = stack.pop(); //якщо інцидентної не знайдено граф повернеться до тієї вершини яка має й інші ребра
            else {
                way.add(vertexList.getNode(incident).v.name);
                if(vertexList.getNode(incident).v.name == vertexList.getNode(endIndex).v.name){
                    way.print();
                    System.out.println("|The path is found|");
                    break;
                }
                vertexList.getNode(incident).v.isVisited = true;
                stack.push(incident);
            }
        }
        if(stack.isEmpty())System.out.println("U can`t get to this vertex");
        for(int i =0; i<vertSize;i++){ // повернення вказівників на відвідані вершини до початкових значень
            vertexList.getNode(i).v.isVisited = false;
        }
    }
    public void passInWidth(String toFind){
        Queue queue = new Queue();
        List<String> way = new List<>();
        int index = 0;
        int endIndex = getIndexByName(toFind);
        way.add(vertexList.getNode(index).v.name);
        vertexList.getNode(index).v.isVisited = true;
        queue.insert(index);

        int incident, temp;
        while(!queue.isEmpty()){
            temp = queue.remove(); // з черги видаляється елемент якщо всі ребра, які йдуть з даної вершини вже пройдені
            while((incident = check(temp)) !=-1){ // цикл виконується поки для вершини є інцидентні
                way.add(vertexList.getNode(incident).v.name);
                if(vertexList.getNode(incident).v.name == vertexList.getNode(endIndex).v.name){
                    way.print();
                    System.out.println("|The path is found|");
                    for(int i =0; i<vertSize;i++){ // повернення вказівників на відвідані вершини до початкових значень
                        vertexList.getNode(i).v.isVisited = false;
                    }
                    return;
                }
                vertexList.getNode(incident).v.isVisited = true;
                queue.insert(incident);
            }
        }
        System.out.println("U can`t get to this vertex");
        for(int i =0; i<vertSize;i++){ // повернення вказівників на відвідані вершини до початкових значень
            vertexList.getNode(i).v.isVisited = false;
        }
    }

    public void dijkstra(String toFind){
        int getPathTo = getIndexByName(toFind);
        int distance[] = new int[adjMatrix.length];
        distance[0]=0;
        for(int i=1; i < adjMatrix.length; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        for(int i=0; i < adjMatrix.length-1; i++){
            int minVertex = findMinVertex(distance);
            vertexList.getNode(minVertex).v.isVisited = true; //вказуємо, що вершина пройдена
            for(int j=0; j < adjMatrix.length; j++){
                if(adjMatrix[minVertex][j] !=0 && !vertexList.getNode(j).v.isVisited&& distance[minVertex] != Integer.MAX_VALUE){
                    int newDist = distance[minVertex] + adjMatrix[minVertex][j]; // знаходимо відстань до вершини з урахуванням відстані до попередньої
                    if(newDist < distance[j]) distance[j] = newDist; //знаходимо найменшу відстань
                }
            }
        }
        if(distance[getPathTo] != Integer.MAX_VALUE) System.out.println("Me --> "+toFind+" = "+distance[getPathTo]+"ms");
        else System.out.println("U can`t get to this vertex");
        for(int i =0; i<vertSize;i++){
            vertexList.getNode(i).v.isVisited = false;
        }
    }

    private int findMinVertex(int[] distance){ // пошук непройденої вершини з мінімальною дистанцією
        int minVertex =-1;
        for(int i =0; i < distance.length; i++){
            if(!vertexList.getNode(i).v.isVisited && (minVertex == -1 || distance[i] < distance[minVertex])){
                minVertex =i;
            }
        }
        return minVertex;
    }

    public void vertexBreak(String toDelete){
        int index = getIndexByName(toDelete);
        if(index <0){
            System.out.println("Index is out of bounds");
            return;
        }
        vertexList.remove(index);
        vertSize--;
        int s=0, k=0;
        int[][] newAdjMatrix = new int[adjMatrix.length-1][adjMatrix.length-1];
        for(int i = 0; i < adjMatrix.length; i++){
            if( i == index) continue;
            for(int j = 0; j< adjMatrix.length;j++){
                if(j == index) continue;
                newAdjMatrix[s][k] = adjMatrix[i][j];
                k++;
            }
            k=0;
            s++;
        }
        setAdjMatrix(newAdjMatrix);

        System.out.println("<-Vertex destroyed->");
    }
    public int getIndexByName(String str){
        List<Vertex>.Node curr = vertexList.head;
        int count =0;
        while(curr != null){
            if(Objects.equals(str, vertexList.getNode(count).v.name)) return count;
            curr = curr.next;
            count++;
        }
        return -1;
    }
    public void startProgram(BinaryTree tree){
        initialize();
        String vert;
        Scanner scan = new Scanner(System.in);
        while(true){
            while(true){
                System.out.println("Do you want any vertex to break? (y/n) ");
                String brk = scan.nextLine();
                if(brk.equals("y") || brk.equals("Y")){
                    System.out.print("Enter IP: ");
                    String vertex = scan.nextLine();
                    vertexBreak(vertex);
                }
                if(brk.equals("n") || brk.equals("N"))break;
            }
            System.out.print("Enter the address you want to connect: ");
            String address =scan.nextLine();
            BinaryTree.Node node = tree.findNodeByKey(address);
            if(node == null){
                System.out.println("Website does not exist!");
            } else {
                if(node.ipList.size > 1){
                    System.out.print("Website has a few addresses: ");
                    List<String>.Node curr = node.ipList.head;
                    while(curr != null){
                        System.out.print(curr.v + "  ");
                        curr = curr.next;
                    }
                    while(true){
                        System.out.print("\nYou can choose one, press 0, 1, 2...etc: ");
                        int choose = scan.nextInt();
                        if(choose < node.ipList.size){
                            vert = node.ipList.getNode(choose).v;
                            break;
                        }
                        System.out.println("Index out of bounds, please try again");
                    }
                    scan.nextLine();
                } else  vert = node.ipList.head.v;

                if(getIndexByName(vert) == -1){
                    System.out.println("IP of such website does not exist!");
                } else {
                    System.out.print("BFS: ");
                    passInWidth(vert);
                    System.out.print("DFS: ");
                    passInDeep(vert);
                    System.out.print("Dijkstra: ");
                    dijkstra(vert);
                }


            }
            while(true){
                System.out.println("Continue? (y/n)");
                String c = scan.nextLine();
                if(c.equals("y") || c.equals("Y")) break;
                if(c.equals("n") || c.equals("N")) return;
                System.out.println("Invalid input");
            }

        }

    }
}
class Vertex{
    public String name;
    public boolean isVisited;
    public Vertex(String name){
        this.name = name;
        isVisited=false;
    }
}
