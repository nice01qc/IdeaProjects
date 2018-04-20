import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int num = scanner.nextInt();
        List<Integer> list = new LinkedList<Integer>();
        int max = 0;
        for (int i = 0; i < num; i++){
            int tmp = scanner.nextInt();
            list.add(tmp);
            if (tmp > max) max = tmp;
        }

        List<Long> list1 = new LinkedList<Long>();
        long startNumber = 2;
        while (list1.size() <= max){
            if (IsPrime(startNumber,list1)){
                list1.add(startNumber++);

            }else {
                startNumber++;
            }
        }

        for (int i  = 0; i < list.size(); i++){
            System.out.println(list1.get(i));
        }

    }


    static boolean IsPrime(long number, List<Long> list)//判断number是否为素数
    {
        if (number == 1) return false;
        long max = (long)Math.sqrt(number);
        for (long n : list)
        {
            if (number % n == 0) return false;
            if (n > max) break;
        }
        return true;
    }
}
