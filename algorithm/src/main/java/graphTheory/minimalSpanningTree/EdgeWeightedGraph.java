package graphTheory.minimalSpanningTree;

import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedGraph {
    private final int V;    //顶点总数
    private int E;          //边的总数
    private Bag<Edge>[] adj;
    private Bag<Edge> edges=new Bag<Edge>();

    public EdgeWeightedGraph(int V){
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v=0;v<adj.length;v++){
            adj[v] = new Bag<Edge>();
        }
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w =  e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        edges.add(e);
        E++;
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
    }

    public Iterable<Edge> edges(){
        return edges;
    }

}
