package graphTheory.minimalSpanningTree;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * 最小生成树的Prim算法的延时实现
 */
public class LazyPrimMST {
    private boolean[] marked;   //最小生成树的顶点
    private Queue<Edge> mst;    //最小生成树的边
    private MinPQ<Edge> pq;     //横切边（包括失效的边）

    public LazyPrimMST(EdgeWeightedGraph G){
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        mst =new ConcurrentLinkedQueue<Edge>();

        visit(G,0);     //假设G是连通的
        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either();
            int w =  e.other(v);
            if (marked[v]&&marked[w]) continue;
            mst.add(e);
            if (!marked[v]) visit(G,v);
            if (!marked[w]) visit(G,w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e:G.adj(v))if (!marked[e.other(v)])pq.insert(e);
    }
    public Iterable<Edge> edges(){
        return mst;
    }

    public double weight(){
        double result =0.0;
        for (Edge edge : mst){
            result+=edge.getWeight();
        }
        return result;
    }
}
