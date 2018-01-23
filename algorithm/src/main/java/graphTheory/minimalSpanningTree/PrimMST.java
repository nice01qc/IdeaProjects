package graphTheory.minimalSpanningTree;

import edu.princeton.cs.algs4.Bag;
import passage2.IndexMinPQ;

/**
 * 最小生成树Prim算法（即时版）
 */
public class PrimMST {
    private Edge[] edgeTo;      //距离树最近的边
    private double[] distTo;    //distTo[w] = edgeTo[w].weight()
    private boolean[] marked;   //如果V在树中则为true
    private IndexMinPQ<Double> pq;  //有效的横切边

    public PrimMST(EdgeWeightedGraph G){
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v=0;v<G.V();v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        pq = new IndexMinPQ<Double>(G.V());
        distTo[0] = 0.0;
        pq.insert(0,0.0);
        while(!pq.isEmpty()){
            visit(G,pq.delMin());
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e:G.adj(v)){
            int w = e.other(v);
            if (marked[w])continue;
            if (e.getWeight()<distTo[w]){
                edgeTo[w] = e;
                distTo[w] = e.getWeight();
                if (pq.contains(w))pq.change(w,distTo[w]);
                else               pq.insert(w,distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges(){
        Bag<Edge> bag = new Bag<Edge>();
        for (int i=0;i<edgeTo.length;i++){
            if (edgeTo[i] != null){
                bag.add(edgeTo[i]);
            }

        }
        return bag;
    }

    public double weight(){
        double result =0.0;
        for (int i=0;i<distTo.length;i++){
            result+=distTo[i];
        }
        return result;
    }


}
