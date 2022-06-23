package com.company;

public class List <Item>{
    public Node head;
    public int size;
    public List(){
        head = null;
        size=0;
    }
    public class Node {
        public Node next;
        Item v;
        public Node(Item v){
            this.v = v;
            next = null;
        }
    }
    public boolean isEmpty(){
        return head == null;
    }
    public Node getNode(int place){
        if(place < 0)return null;
        Node cur = head;
        int count = 0;
        while(count != place){
            count++;
            cur = cur.next;
        }
        return cur;
    }
    public void add(Item v){
        Node newNode = new Node(v);
        Node currentNode = head;
        if (head == null){
            head = newNode;
        }
        else{
            while(currentNode.next != null){
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }
        size++;
    }
    public void remove(int index){
        if(index < 0){
            System.out.println("Index out of bounds");
            return;
        }
        int count=0;
        Node currentNode = head;
        Node previousNode = null;

        while(currentNode != null){
            if(count == index){
                if(currentNode == head)
                    head = currentNode.next;
                else
                    previousNode.next =currentNode.next;
            }
            count++;
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        size--;
    }
    public void print(){
        if(head == null){
            System.out.println("List is empty");
            System.exit(0);
        }
        Node currentNode = head;
        while(currentNode != null){
            System.out.print(currentNode.v +" -->\t");
            currentNode = currentNode.next;
        }
    }

}
