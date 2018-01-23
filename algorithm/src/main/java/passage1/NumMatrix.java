package passage1;

public class NumMatrix {
    int[][] matrix;

    public NumMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int result = 0;
        if (row1>matrix.length&&row2>matrix.length){
            return -1;
        }
        if (col1>matrix[0].length&&col2>matrix[0].length){
            return -1;
        }

        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                result += matrix[row][col];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}
        };
        NumMatrix obj = new NumMatrix(matrix);
        int param_1 = obj.sumRegion(2,1,4,3);
        System.out.println(param_1);
    }
}
