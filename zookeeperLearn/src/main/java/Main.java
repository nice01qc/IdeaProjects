import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] firstLine = in.nextLine().split(" ");
        int row = Integer.parseInt(firstLine[0]);
        int col = Integer.parseInt(firstLine[1]);
        int[][] datas = new int[row][col];
        for (int i = 0; i < row; i++){
            String[] tmp = in.nextLine().split(" ");
            for (int j = 0; j < col; j++){
                datas[i][j] = Integer.parseInt(tmp[j]);
            }
        }
        System.out.println(computePoolNum(datas));
    }
    private static int computePoolNum(int[][] a) {
        int num = 0;
        int row = a.length;
        int col = a[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (a[i][j] == 1) {
                    num++;
                    changeState(a, i, j);
                }
            }
        }
        return num;
    }
    private static void changeState(int[][] a, int x, int y) {
        a[x][y] = 0;
        for (int i = x - 1; i >= 0; i--) {
            if (a[i][y] == 1) changeState(a, i, y);
            else break;
        }
        for (int i = x + 1; i < a.length; i++) {
            if (a[i][y] == 1) changeState(a, i, y);
            else break;
        }
        for (int j = y - 1; j >=0; j--) {
            if (a[x][j] == 1) changeState(a, x, j);
            else break;
        }
        for (int j = y + 1; j < a[0].length; j++) {
            if (a[x][j] == 1) changeState(a, x, j);
            else break;
        }
    }
}
