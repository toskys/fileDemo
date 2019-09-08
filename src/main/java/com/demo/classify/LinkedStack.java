package com.demo.classify;

import com.demo.entity.Employee;

//链表实现栈
public class LinkedStack {
    private Node top;   //指向栈顶数据
    private Node head;  //头结点
    private int length = 0; //栈中数据个数
    public LinkedStack(){
        this.head = new Node();
        this.top = this.head;
    }

    /**
     * 入栈
     */
    public boolean push(Employee employee){
        Node node = new Node(employee);
        if(isEmpty()){
            this.head.next = node;
            this.top = node;
            length++;
            return true;
        }
        node.next = this.top;
        this.head.next = node;
        this.top = node;
        length++;
        return true;
    }

    /**
     * 出栈
     */
    public Employee pop(){
        if(isEmpty()){ return null; }
        Employee employee = this.top.data;
        this.head.next = this.top.next;
        this.top = this.top.next;
        length--;
        return employee;
    }
    /**
     * 栈是否为空
     */
    public boolean isEmpty(){
        return this.head.next == null;
    }

    /**
     *  格式化显示栈中所有数据
     */
    public String formatStack(){
        if (isEmpty()){ return "[]";}
        Node temp = this.head.next;
        String str = "";
        while (temp!=null){
            str += temp.data.toString() + "\n";
            temp = temp.next;
        }
        return str;
    }

    /**
     * 结点
     */
    class Node{
        private Employee data;
        private Node next;
        public Node(){}
        public Node(Employee employee){
            this.data =employee;
        }
    }
}
