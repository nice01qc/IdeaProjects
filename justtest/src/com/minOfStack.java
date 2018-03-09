package com;

import java.util.*;

/**
 * 定义一个栈的数据结构，得到栈中最小的元素
 */
public class minOfStack {
    Stack<Integer> stack = new Stack<Integer>();
    int temp;
    public void push(int node) {
        stack.push(node);
    }

    //将栈顶元素删除
    public void pop() {
      stack.pop();
    }

    public int top() {
        if(!stack.isEmpty()) {
            return stack.peek();
        }else {
            return -1;
        }
    }
    // 你这一个min() 可是把元素都删到只剩下一个了哟
    public int min() {
        Iterator<Integer> integerIterator = stack.iterator();
        int result = 0;
        if (integerIterator.hasNext())
            result = integerIterator.next();
        while(integerIterator.hasNext()){
            int x = integerIterator.next();
            if(x < result){
                result = x;
            }
        }
        return result;
    }


    public static void main(String[] args) {
//        minOfStack min = new minOfStack();
//        min.push(10);
//        min.push(5);
//        min.push(30);
//        min.push(8);
//
//
//        Set<String> set = new HashSet<>();
//
//
//        System.out.println(min.min());

        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.peek());

    }
}









