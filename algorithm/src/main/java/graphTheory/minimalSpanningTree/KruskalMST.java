package graphTheory.minimalSpanningTree;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.UF;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KruskalMST {
    private Queue<Edge> mst=new ConcurrentLinkedQueue<Edge>();

    public KruskalMST(EdgeWeightedGraph G){
        boolean[] marked=new boolean[G.V()];
        Comparator<Edge> comparator =new Comparator<Edge>() {
            public int compare(Edge v, Edge w) {
                if (v.getWeight()<w.getWeight()){
                    return -1;
                }else if (v.getWeight()>w.getWeight()){
                    return 1;
                }else return 0;
            }
        };
        MinPQ<Edge> minPQ = new MinPQ<Edge>(comparator);
        for (Edge e:G.edges()){
            minPQ.insert(e);
        }
        UF uf = new UF(G.V());
        do {
            Edge minEdge = minPQ.delMin();
            int v = minEdge.either();
            int w = minEdge.other(v);
           
                if (uf.connected(v,w))continue;
                else uf.union(w,v);
                mst.add(minEdge);
                marked[w]=true;
                marked[v]=true;

        }while (!minPQ.isEmpty() && mst.size()<G.V()-1);

    }


    public Iterable<Edge> edges(){
        return mst;
    }
}
