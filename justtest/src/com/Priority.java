package com;

public class Priority<T extends Comparable<T>> {

    int maxlength = 10;
    int root = 1;
    int N = 0;

    T priorityArray[];


    public Priority(){
        priorityArray = (T[]) new Comparable[maxlength];
    }

    private void resize(){
        if (N < maxlength/2) return;
        maxlength = 2 * maxlength;
        T[] newpriorityArray = (T[]) new Comparable[maxlength];
        for (int i = 1; i <= N; i++){
            newpriorityArray[i] = priorityArray[i];
        }
        priorityArray = newpriorityArray;
    }

    public void insert(T t){
        priorityArray[++N] = t;
        swin(N);
    }

    public T deMax(){
        T result = priorityArray[1];
        sink(1);
        priorityArray[N] = null;
        N--;
        return  result;
    }


    public int getSize(){
        return N;
    }



    // 用于添加元素上浮
    private void swin(int k){
        int j;
        while (k > 1){
            j = k / 2;
            if (less(k, j))break;
            exch(k,j);
            k = j;
        }
    }

    // 用于下沉，删除元素
    private void sink(int k){
        if (k >= N) return;
        int j;

        while(2*k <= N){
            j = 2*k;
            if (j < N && less(j,j+1)) j++;
            if (less(k,j)) break;
            exch(j,k);
            k = j;
        }
    }







    private boolean less(int a, int b){
        return priorityArray[a].compareTo(priorityArray[b]) < 0;
    }

    private void exch(int a, int b){
        T temp = priorityArray[a];
        priorityArray[a] = priorityArray[b];
        priorityArray[b] = temp;
    }


}
