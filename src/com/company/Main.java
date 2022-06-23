package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        File fileVert = new File("vertexes");
        Scanner scan1 = new Scanner(fileVert);
        String amountOfVertexes =scan1.nextLine();
        int amount = Integer.parseInt(amountOfVertexes);
        Graph graph = new Graph(amount);
        String label ="";
        while(scan1.hasNextLine()){
            label = scan1.nextLine();
            graph.addVertex(label);
        }
        scan1.close();

        File fileAdj = new File("adjacencyMatrix");
        Scanner scan2 = new Scanner(fileAdj);
        int[] num = new int[3];
        int count =0;
        while(scan2.hasNextLine()){
            String line = scan2.nextLine();
            String[] numbersString = line.split(" ");

            for(String str: numbersString){
                num[count++]= Integer.parseInt(str);
            }
            count=0;
            graph.addEdge(num[0],num[1], num[2]);
        }
        scan2.close();

        BinaryTree tree = new BinaryTree();
        File fileDns = new File("DnsTable");
        Scanner scan3 = new Scanner(fileDns);
        while(scan3.hasNextLine()){
            String lines = scan3.nextLine();
            String[] str = lines.split(" ");
            tree.add(str[0], str[1]);
        }
        scan3.close();

        graph.startProgram(tree);

    }
}



