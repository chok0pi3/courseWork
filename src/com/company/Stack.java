package com.company;

public class Stack {
    private int size =0;
    private Node top = null;
    private class Node {
        int data;
        Node next;
    }
    public boolean isEmpty() {
        return top == null;
    }

    public void push(int data) {
        Node oldTop = top;
        top = new Node();
        top.data = data;
        top.next = oldTop;
        size++;
    }
    public int pop() {
        int data = top.data;
        top = top.next;
        return data;
    }
    public int peek(){
        if(top != null) {return top.data;}
        return -1;
    }
    public int size(){
        return size;
    }
}