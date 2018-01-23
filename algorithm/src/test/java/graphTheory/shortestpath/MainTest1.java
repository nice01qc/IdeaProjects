package graphTheory.shortestpath;

import org.junit.Test;

public class MainTest1 {
    @Test
    public void test(){
        DirectedEdge d1 = new DirectedEdge(5,4,0.35);
        DirectedEdge d2 = new DirectedEdge(4,7,0.37);
        DirectedEdge d3 = new DirectedEdge(5,7,0.28);
        DirectedEdge d5 = new DirectedEdge(5,1,0.32);
        DirectedEdge d6 = new DirectedEdge(4,0,0.38);
        DirectedEdge d7 = new DirectedEdge(0,2,0.26);
        DirectedEdge d8 = new DirectedEdge(3,7,0.39);
        DirectedEdge d9 = new DirectedEdge(1,3,0.29);
        DirectedEdge d10 = new DirectedEdge(7,2,0.34);
        DirectedEdge d11 = new DirectedEdge(6,2,0.4);
        DirectedEdge d12 = new DirectedEdge(3,6,0.52);
        DirectedEdge d13 = new DirectedEdge(6,0,0.58);
        DirectedEdge d14 = new DirectedEdge(6,4,0.93);


        EdgeWeightedDigraph e  = new EdgeWeightedDigraph(8);
        e.addEdge(d1);
        e.addEdge(d2);
        e.addEdge(d3);
        e.addEdge(d5);
        e.addEdge(d6);
        e.addEdge(d7);
        e.addEdge(d8);
        e.addEdge(d9);
        e.addEdge(d10);
        e.addEdge(d11);
        e.addEdge(d12);
        e.addEdge(d13);
        e.addEdge(d14);

        DijkstraSP dijkstraSP = new DijkstraSP(e,1);
        System.out.println(dijkstraSP.distTo(2));
        Iterable<DirectedEdge> iterable = dijkstraSP.pathTo(2);
        System.out.println();
        for (DirectedEdge directedEdge:iterable){
            System.out.print(directedEdge+"\t");
        }
    }
}
