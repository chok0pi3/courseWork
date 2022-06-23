package com.company;

public class BinaryTree {
    public Node rootNode;

    public BinaryTree() {
        rootNode = null;
    }
    public static class Node{
        String dns;
        List<String> ipList ;
        Node right;
        Node left;
        public Node(String dns, String ip){
            ipList = new List<>();
            this.ipList.add(ip);
            this.dns = dns;
            right = null;
            left = null;
        }
    }
    public Node findNodeByKey(String value) {
        Node curr = rootNode;
        while (curr.dns.compareTo(value) !=0){
            if (value.compareTo(curr.dns) <0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr == null) {
                return null;
            }
        }
        return curr;
    }

    public void add(String dns, String ip){ //додавання нового елемента
        Node newNode = new Node(dns, ip);
        if( rootNode == null) rootNode = newNode;
        else{
            Node curr = rootNode;
            Node parentNode;
            while(true) {
                parentNode = curr;
                if (dns.compareTo(curr.dns)==0) { curr.ipList.add(ip); return;
                } else if (dns.compareTo(curr.dns)<0) {   // якщо значення менше йдемо ліворуч
                    curr = curr.left;
                    if (curr == null) { // Якщо дійшли до кінця, вставляємо новий елемент
                        parentNode.left = newNode;
                        return;
                    }
                } else{ //Тобто елемент більший і ми йдемо праворуч
                    curr = curr.right;
                    if (curr == null) { // Якщо дійшли до кінця, вставляємо новий елемент
                        parentNode.right = newNode;
                        return;
                    }
                }
            }
        }
    }
    public void preorder(Node node){ //обхід дерева та виведення дерева
        if (node == null)
            return;
        System.out.print(node.dns + "->");
        preorder(node.left);
        preorder(node.right);
    }

}

