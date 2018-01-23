package graphTheory.undirectedGrap;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class SymbolGraph {
    private HashMap<String,Integer> st;         //符号名-->索引
    private String[] keys;                      //索引-->符号名
    private Graph G;                            //图

    public SymbolGraph(String stream,String sp){
        st = new HashMap<String, Integer>();
        In in = new In(stream);
        while(in.hasNextLine()){
            String[] a = in.readLine().split(sp);

            for (int i =0;i<a.length;i++){
                if (!st.containsKey(a[i])){
                    st.put(a[i],st.size());
                }
            }
        }

        keys = new String[st.size()];
        for (String name : st.keySet()){
            keys[st.get(name)] = name;
        }

        G = new Graph(st.size());
        in = new In(stream);
        while(in.hasNextLine()){
            String[] a = in.readLine().split(sp);
            int v = st.get(a[0]);
            for (int i =1;i<a.length;i++){
                G.addEdge(v,st.get(a[i]));
            }
        }
    }

    public boolean contains(String s){
        return st.containsKey(s);
    }

    public int index(String s){
        return st.get(s);
    }

    public Graph G(){
        return G;
    }
}
