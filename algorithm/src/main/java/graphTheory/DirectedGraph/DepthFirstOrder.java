package graphTheory.DirectedGraph;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

//有向图中基于优先搜索的顶点排序
public class DepthFirstOrder {
    private boolean[] marked;
    private Queue<Integer> pre;             //所有顶点的前序排列
    private Queue<Integer> post;            //所有顶点的后序排列
    private Stack<Integer> reversePost;     //所有定点逆后序排列

    public DepthFirstOrder(Digraph G){
        pre = new ConcurrentLinkedQueue<Integer>();
        post = new ConcurrentLinkedQueue<Integer>();
        reversePost = new Stack<Integer>();

        marked = new boolean[G.V()];

        for (int v = 0;v<G.V();v++){
            if (!marked[v]){
                dfs(G,v);
            }
        }
    }

    private void dfs(Digraph G,int v){
        pre.add(v);
        marked[v] =  true;
        for (int w:G.adj(v)){
            if (!marked[w]){
                dfs(G,w);
            }
        }
        post.add(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre(){
        return pre;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public Iterable<Integer> reversePost(){
        Queue<Integer> replace = new ConcurrentLinkedQueue<Integer>();
        while(!reversePost.empty())
        {
            replace.add(reversePost.pop());
        }

        return replace;
    }





}
