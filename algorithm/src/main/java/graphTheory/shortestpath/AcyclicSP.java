package graphTheory.shortestpath;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;

/**
 * 无环加权有向图的最短路径算法
 */
public class AcyclicSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph G,int s){
        //初始化
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        distTo[s] = 0.0;
        for (int v=0;v<G.V();v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        //深度优先搜索搞成拓扑
        Digraph digraph = new Digraph(G.V());
        for (DirectedEdge directedEdge : G.edges()){
            digraph.addEdge(directedEdge.from(),directedEdge.to());
        }
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(digraph);
        Iterable<Integer> a = depthFirstOrder.reversePost();

        for (int v:a){
//            int pan=0;
//            if (v==s){
//                relax(G,v);
//                pan = 1;
//            }else if (pan==1){
//                relax(G,v);
//            }
            relax(G,v); //刚好利用了无穷大与无穷大比较为false的结果来跳过
        }
    }

    private void relax(EdgeWeightedDigraph G,int v){
        for (DirectedEdge e:G.adj(v)){
            int w = e.to();
            if (distTo[v]+e.weight()<distTo[w]){
                distTo[w] = distTo[v]+e.weight();
                edgeTo[w] = e;
            }
        }
    }

    public double distTo(int v){
        return distTo[v];
    }

    public boolean hasPathTo(int v){
        return distTo[v]<Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v){
        Bag<DirectedEdge> bag = new Bag<DirectedEdge>();

        for (DirectedEdge d = edgeTo[v];d!=null;d=edgeTo[d.from()]){
            bag.add(d);
        }
        return bag;
    }
}
