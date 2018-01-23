


import java.util.*;

public class Main {
    public static class Graph {
        private int V;        //定点数目
        private int E;              //边的数目
        private List<Integer>[] adj; //邻接表

        public Graph(int V) {
            this.V = V;
            this.E = 0;
            adj = new ArrayList[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new ArrayList<Integer>();
            }
        }

        //无重复插入
        public void addEdge(int v, int w) {
            Iterator<Integer> iteratorv = adj[v].iterator();
            while (iteratorv.hasNext()) {
                if (iteratorv.next().equals(w)) {
                    v = -1;
                }
            }
            if (v != -1) adj[v].add(w);

            Iterator<Integer> iteratorw = adj[w].iterator();
            while (iteratorw.hasNext()) {
                if (iteratorw.next().equals(v)) {
                    w = -1;
                }
            }
            if (w != -1) adj[w].add(v);

            E++;
        }

        public int V() {
            return V;
        }

        public int E() {
            return E;
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }
    }

    public static int getNum(int a){
        int i = 1;
        if (a <= 2) return 1;
        int bb =2;
        int begin = 3;
        while(bb <= a){
            i++;
            bb *= begin++;
        }

        return i;
    }


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);


        int vertices, edges;
        vertices = in.nextInt();
        edges = in.nextInt();
        HashMap<Integer,Integer> zhi1 = new HashMap<Integer, Integer>();
        HashMap<Integer,Integer> zhi2 = new HashMap<Integer, Integer>();
        for (int i = 0;i<vertices;i++){
            int temp =in.nextInt();
            zhi1.put(temp,i);
            zhi2.put(i,temp);
        }
        HashMap<String,Integer> distance = new HashMap<String, Integer>();

        Graph graph = new Graph(vertices);
        for (int i = 0; i < edges; i++){
            int a = in.nextInt();
            int b = in.nextInt();
            graph.addEdge(zhi1.get(a), zhi1.get(b));
            distance.put(zhi1.get(a)+"_"+zhi1.get(b),in.nextInt());
            distance.put(zhi1.get(b)+"_"+zhi1.get(a),distance.get(zhi1.get(a)+"_"+zhi1.get(b)));
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i=0;i<vertices;i++){
            if (zhi2.get(i) < min)min = zhi2.get(i);
            if (zhi2.get(i) > max)max = zhi2.get(i);
        }
        int minNum = zhi1.get(min);
        int maxNum = zhi1.get(max);


        List<Integer> list = new ArrayList<Integer>();
        list.add(zhi2.get(minNum));
        int pan = minNum;
        boolean[] marked = new boolean[vertices];
        while(pan != maxNum){
            marked[pan] = true;
            int minVale = Integer.MAX_VALUE;
            int next = pan;
            for (int v : graph.adj(pan)){
                if (!marked[v]){
                    if (minVale > distance.get(pan+"_"+v)){
                        next = v;
                        minVale = distance.get(pan+"_"+v);
                    }
                }
            }
            pan = next;
            list.add(zhi2.get(next));
        }

        int result = 0;
        for (int a : list){
            result += getNum(a);
        }
        System.out.println(result);











    }


}
