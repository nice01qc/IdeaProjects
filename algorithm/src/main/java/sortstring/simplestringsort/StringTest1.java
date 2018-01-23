package sortstring.simplestringsort;

//低位优先的字符串排序
public class StringTest1 {
    public static void sort(String[] a,int w){
        //通过前w个字符将a[] 排序
        int N = a.length;
        int R = 256;
        String[] aux= new String[N];

        for (int d = 0;d<w;d++){
            //根据第d个字符用键索引计数法排序
            int[] count = new int[R+1];     //计算出现的频率
            for (int i=0;i<N;i++){
                count[a[i].charAt(d)+1]++;
            }
            for (int r=0;r<R;r++){          //将频率转换为索引
                count[r+1] +=count[r];
            }
            for (int i=0;i<N;i++){          //将元素分类
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            for (int i=0;i<N;i++){          //回写
                a[i] = aux[i];
            }
            myout(a);
        }
    }

    public static void myout(String[] a){
        System.out.println();
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+"\t");
        }
        System.out.println();
    }

    public static void main(String[] args){
            String[] data = new String[]{
                    "4PGC938","2IYE230","3CI0720","1ICK750",
                    "126V845","4JZY520","1ICK750","3CI750",
                    "11HV845","121V845","2RLA629","24LA629","3ATW723"
            };
            StringTest1.sort(data,3);
             StringTest1.myout(data);

    }



    class na{
        String name;
        int key;
        public na(String name,int key){
            this.name = name;
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public int getKey() {
            return key;
        }
    }
}
