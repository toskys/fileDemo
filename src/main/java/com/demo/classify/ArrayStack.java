package com.demo.classify;


import com.demo.entity.Employee;

//数据实现栈
public class ArrayStack  {
    private int maxSize;   //栈数组的最大容量
    public int length = 0;  //栈中数据的个数
    private int top = -1;   //栈顶索引，默认为-1，指向最顶端数据
    private Employee[] stack;   //栈数组

    public ArrayStack(){
        this.maxSize = 10;
        this.stack = new Employee[this.maxSize];
    }

    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        this.stack = new Employee[this.maxSize];
    }

    /**
     * 入栈
     */
    public boolean push(Employee employee){
        if (isFull()){ return false; }
        this.top++;
        this.stack[this.top] = employee;
        this.length++;
        return true;
    }

    /**
     * 出栈
     */
    public Employee pop(){
        if(isEmpty()){ return null; }
        Employee employee = this.stack[this.top];
        this.top--;
        this.length--;
        return employee;
    }
    /**
     * 栈是否为空
     */
    public boolean isEmpty(){
        return this.top == -1;
    }

    /**
     * 栈是否已满
     */
    public boolean isFull(){
        return this.top == this.maxSize-1;
    }

    /**
     *  格式化显示栈中所有数据
     */
    public String formatStack(){
        if (isEmpty()){ return "[]";}
        String str = "";
        for (int i = this.length-1;i>=0;i--){
            str += this.stack[i].toString()+"\n";
        }
        return str;
    }
}
