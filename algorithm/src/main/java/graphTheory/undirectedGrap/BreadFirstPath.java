package graphTheory.undirectedGrap;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class BreadFirstPath {
    private boolean[] marked;       //到达该顶点的最短路径已知吗？
    private int[] edgeTo;           //到达该顶点的已知路径上的最后一个顶点
    private final int s;            //  起点

    public BreadFirstPath(Graph G,int s){
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.s = s;
        bfs(G,s);
    }

    private void bfs(Graph G,int s){
        Queue<Integer> queue = new PriorityQueue<Integer>();
        marked[s] = true;
        queue.add(s);
        while(!queue.isEmpty()){
            int v = queue.remove();
            for (int w : G.adj(v)) {
                if (!marked[w]){
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){

        if (!hasPathTo(v))return null;

        Stack<Integer> stack = new Stack<Integer>();
        for (int i = v;i!=s;i = edgeTo[i]){
            stack.push(i);
        }
        stack.push(s);
        return stack;
    }
}
