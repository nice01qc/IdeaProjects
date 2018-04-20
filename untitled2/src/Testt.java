public class Testt {



    public void pan(int[] array){
        if (array == null) return;

        int length = array.length;
        if (length == 1){
            System.out.println(array[0]);   // 输出结果
            return;
        }

        int elementNum = 0;
        int tmp = array[0];
        for (int i = 1; i < array.length; i++){
            if (array[i] == tmp){
                elementNum++;
            }else {
                tmp = array[i];
                elementNum = 0;
            }
            if (elementNum >= length/2){
                System.out.println(array[i]);       // 输出结果
                while (++i < array.length && array[i] == tmp);
                i--;
                elementNum=0;
            }
        }


    }







}
