package com.company;

public class Queue  {
    private Node head;
    private Node tail;
    private int size;

    public Queue(){
        size=0;
        head =null;
        tail = null;
    }
    public static class Node{
        public int data;
        public Node next;
        public Node(int data){
            this.data = data;
            next = null;
        }
    }
    public boolean isEmpty(){
        return head ==null;
    }
    public void insert(int data){
        Node curr = head;
        if (isEmpty()){
            head = new Node(data);
        }
        else{
            while(curr.next != null){
                curr = curr.next;
            }
            curr.next = new Node(data);
            tail = curr.next;
        }
        size++;
    }
    public int remove(){
        if(!isEmpty()){
            int remove = head.data;
            head = head.next;
            size--;
            return remove;
        } else {
            throw new NullPointerException("Queue is empty");
        }
    }
    public void printInfo(){
        Node curr = head;
        if(!isEmpty()){
            while(curr !=null){
                System.out.print(curr.data+"\t");
                curr = curr.next;
            }
            System.out.println("|The head data = "+head.data+", the tail data = "+tail.data+", size of Queue = "+size+"|");
        }
        else{
            System.out.println("Queue is empty, add some elements");
        }
    }
}
