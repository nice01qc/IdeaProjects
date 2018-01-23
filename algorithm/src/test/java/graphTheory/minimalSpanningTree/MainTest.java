package graphTheory.minimalSpanningTree;

import org.junit.Test;
import passage2.IndexMinPQ;

public class MainTest {
    @Test
    public void test(){
        Edge edge = new Edge(4,5,0.35);
        Edge edge1 = new Edge(4,7,0.37);
        Edge edge2 = new Edge(5,7,0.28);
        Edge edge3 = new Edge(0,7,0.16);
        Edge edge4 = new Edge(1,5,0.32);
        Edge edge5 = new Edge(0,4,0.38);
        Edge edge6 = new Edge(2,3,0.17);
        Edge edge7 = new Edge(1,7,0.19);
        Edge edge8 = new Edge(0,2,0.26);
        Edge edge9 = new Edge(1,2,0.36);
        Edge edge10 = new Edge(1,3,0.29);
        Edge edge11 = new Edge(2,7,0.34);
        Edge edge12 = new Edge(6,2,0.40);
        Edge edge13 = new Edge(3,6,0.52);
        Edge edge14 = new Edge(6,0,0.58);
        Edge edge15 = new Edge(6,4,0.93);

        EdgeWeightedGraph e = new EdgeWeightedGraph(8);
        e.addEdge(edge);
        e.addEdge(edge1);
        e.addEdge(edge2);
        e.addEdge(edge3);
        e.addEdge(edge4);
        e.addEdge(edge5);
        e.addEdge(edge6);
        e.addEdge(edge7);
        e.addEdge(edge8);
        e.addEdge(edge9);
        e.addEdge(edge10);
        e.addEdge(edge11);
        e.addEdge(edge12);
        e.addEdge(edge13);
        e.addEdge(edge14);
        e.addEdge(edge15);

        KruskalMST p = new KruskalMST(e);
        for (Edge ee:p.edges()){
            System.out.print(ee+"\t");
        }

    }
}
