package graphTheory.DirectedGraph;

import org.junit.Test;

import java.util.Stack;

public class MainTest {

    @Test
    public void test1(){
        Digraph digraph = new Digraph(14);



//        {
//            digraph.addEdge(4,2);
//            digraph.addEdge(2,3);
//            digraph.addEdge(3,2);
//            digraph.addEdge(6,0);
//            digraph.addEdge(0,1);
//            digraph.addEdge(2,0);
//            digraph.addEdge(11,12);
//            digraph.addEdge(12,9);
//            digraph.addEdge(9,10);
//            digraph.addEdge(9,11);
//            digraph.addEdge(8,9);
//            digraph.addEdge(10,12);
//            digraph.addEdge(11,4);
//            digraph.addEdge(4,3);
//            digraph.addEdge(3,5);
//            digraph.addEdge(7,8);
//            digraph.addEdge(8,7);
//            digraph.addEdge(5,4);
//            digraph.addEdge(0,5);
//            digraph.addEdge(6,4);
//            digraph.addEdge(6,9);
//            digraph.addEdge(7,6);
//        }

//        digraph.addEdge(0,6);
//        digraph.addEdge(0,1);
//        digraph.addEdge(0,5);
//        digraph.addEdge(2,0);
//        digraph.addEdge(2,3);
//        digraph.addEdge(3,5);
//        digraph.addEdge(5,4);
//        digraph.addEdge(6,4);
//        digraph.addEdge(6,9);
//        digraph.addEdge(7,6);
//        digraph.addEdge(8,7);
//        digraph.addEdge(9,10);
//        digraph.addEdge(9,12);
//        digraph.addEdge(9,11);
//        digraph.addEdge(11,12);



        {
            digraph.addEdge(13,1);
            digraph.addEdge(13,5);
            digraph.addEdge(2,13);
            digraph.addEdge(2,3);
            digraph.addEdge(3,2);
            digraph.addEdge(3,5);
            digraph.addEdge(4,2);
            digraph.addEdge(4,3);
            digraph.addEdge(5,4);
            digraph.addEdge(6,13);
            digraph.addEdge(6,4);
            digraph.addEdge(6,9);
            digraph.addEdge(7,6);
            digraph.addEdge(7,8);
              digraph.addEdge(9,10);
            digraph.addEdge(9,11);
            digraph.addEdge(10,12);
            digraph.addEdge(11,4);
            digraph.addEdge(11,12);
            digraph.addEdge(12,9);
            digraph.addEdge(4,0);
        }
        System.out.println(Double.POSITIVE_INFINITY);
    }
}











































//{
//        digraph.addEdge(0,6);
//        digraph.addEdge(0,1);
//        digraph.addEdge(0,5);
//        digraph.addEdge(2,0);
//        digraph.addEdge(2,3);
//        digraph.addEdge(3,5);
//        digraph.addEdge(5,4);
//        digraph.addEdge(6,4);
//        digraph.addEdge(6,9);
//        digraph.addEdge(7,6);
//        digraph.addEdge(8,7);
//        digraph.addEdge(9,10);
//        digraph.addEdge(9,12);
//        digraph.addEdge(9,11);
//        digraph.addEdge(11,12);
//        }