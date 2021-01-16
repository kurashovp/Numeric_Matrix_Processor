package processor;

import java.util.Scanner;

public class Main {
    static final String[] menuItems = {
            "1. Add matrices",
            "2. Multiply matrix by a constant",
            "3. Multiply matrices",
            "4. Transpose matrix",
            "5. Calculate a determinant",
            "6. Inverse matrix",
            "0. Exit"
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printMenu();
        int choice = sc.nextInt();
        while (choice != 0) {
            switch (choice) {
                case 1:
                    addMatrices(sc);
                    break;
                case 2:
                    multiplyByConstant(sc);
                    break;
                case 3:
                    multiplyMatrices(sc);
                    break;
                case 4:
                    transposeMatrix(sc);
                    break;
                case 5:
                    determinant(sc);
                    break;
                case 6:
                    inverse(sc);
                    break;
                default:
            }
            printMenu();
            choice = sc.nextInt();
        }

    }

    private static void inverse(Scanner sc) {
        double[][] m = getMatrix(sc, "");
        double det;
        if (m[0].length != m.length) {
            System.out.println("The operation cannot be performed.");
        } else if ((det = determinant(m)) == 0) {
            System.out.println("This matrix doesn't have an inverse.");
        } else {
            printMatrix(inverseMatrix(m, det));
        }
    }

    private static double[][] inverseMatrix(double[][] m, double det) {
        double[][] result = new double[m.length][m.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = cofactor(m, i, j);
            }
        }
        result = multiplyMatrixByConstant(transposeMainDiagonal(result), 1. / det);
        return result;
    }

    private static void determinant(Scanner sc) {
        double[][] m = getMatrix(sc, "");
        if (m[0].length == m.length) {
            System.out.println(determinant(m));
        } else {
            System.out.println("The operation cannot be performed.");
        }
    }

    private static double determinant(double[][] m) {
        if (m.length == 2) {
            return m[0][0] * m[1][1] - m[0][1] * m[1][0];
        } else {
            double det = 0;
            for (int i = 0; i < m.length; i++) {
                det += m[0][i] * cofactor(m, 0, i);
            }
            return det;
        }
    }

    private static double cofactor(double[][] m, int i, int j) {
        return (1 - ((i + j) % 2) * 2) * determinant(minor(m, i, j));
    }

    private static double[][] minor(double[][] m, int i, int j) {
        double[][] minor = new double[m.length - 1][m.length - 1];
        for (int k = 0; k < i; k++) {
            if (j >= 0) System.arraycopy(m[k], 0, minor[k], 0, j);
            if (m.length - (j + 1) >= 0) System.arraycopy(m[k], j + 1, minor[k], j, m.length - (j + 1));
        }
        for (int k = i + 1; k < m.length; k++) {
            if (j >= 0) System.arraycopy(m[k], 0, minor[k - 1], 0, j);
            if (m.length - (j + 1) >= 0) System.arraycopy(m[k], j + 1, minor[k - 1], j, m.length - (j + 1));
        }
        return minor;
    }

    private static void transposeMatrix(Scanner sc) {
        System.out.println("1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line\n" +
                "Your choice: ");
        int transposeType = sc.nextInt();
        double[][] m1 = getMatrix(sc, "");
        switch (transposeType) {
            case 1:
                printMatrix(transposeMainDiagonal(m1));
                break;
            case 2:
                printMatrix(transposeSideDiagonal(m1));
                break;
            case 3:
                printMatrix(transposeVertical(m1));
                break;
            case 4:
                printMatrix(transposeHorizontal(m1));
                break;
            default:
        }
    }

    private static double[][] transposeHorizontal(double[][] m1) {
        double[] temp;
        for (int i = 0; i < m1.length / 2; i++) {
            temp = m1[i];
            m1[i]= m1[m1.length - 1 - i];
            m1[m1.length - 1 - i] = temp;
        }
        return m1;
    }

    private static double[][] transposeVertical(double[][] m1) {
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length / 2; j++) {
                double tmp = m1[i][j];
                m1[i][j] = m1[i][m1[i].length - 1 - j];
                m1[i][m1[i].length - 1 - j] = tmp;
            }
        }
        return m1;
    }

    private static double[][] transposeSideDiagonal(double[][] m1) {
        double[][] mT = new double[m1[0].length][m1.length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                mT[j][i] = m1[m1.length - 1 - i][m1[i].length - 1 - j];
            }
        }
        return mT;
    }

    private static double[][] transposeMainDiagonal(double[][] m1) {
        double[][] mT = new double[m1[0].length][m1.length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                mT[j][i] = m1[i][j];
            }
        }
        return mT;
    }

    private static void multiplyMatrices(Scanner sc) {
        double[][] m1 = getMatrix(sc, "first ");
        double[][] m2 = getMatrix(sc, "second ");
        if (m1[0].length == m2.length) {
            printMatrix(multiplyMatrices(m1, m2));
        } else {
            System.out.println("The operation cannot be performed.");
        }
    }

    private static double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        double[][] mxm = new double[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m1[i].length; k++) {
                    mxm[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mxm;
    }

    private static void multiplyByConstant(Scanner sc) {
        double[][] m = getMatrix(sc, "");
        System.out.print("Enter constant: ");
        double constant = sc.nextDouble();
        printMatrix(multiplyMatrixByConstant(m, constant));
    }

    private static void addMatrices(Scanner sc) {
        double[][] m1 = getMatrix(sc, "first ");
        double[][] m2 = getMatrix(sc, "second ");
        if (m1.length == m2.length && m1[0].length == m2[0].length) {
            printMatrix(addMatrix(m1, m2));
        } else {
            System.out.println("The operation cannot be performed.");
        }
    }

    private static void printMenu() {
        for (String menuItem : menuItems) {
            System.out.println(menuItem);
        }
        System.out.print("Your choice: ");
    }

    private static double[][] multiplyMatrixByConstant(double[][] m, double c) {
        double[][] result = new double[m.length][];
        for (int i = 0; i < m.length; i++) {
            result[i] = new double[m[i].length];
            for (int j = 0; j < m[i].length; j++) {
                result[i][j] = c * m[i][j];
            }
        }
        return result;
    }

    private static void printMatrix(double[][] matrix) {
        System.out.println("The result is: ");
        for (double[] doubles : matrix) {
            for (double d : doubles) {
                System.out.printf("% .2f ", d);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static double[][] addMatrix(double[][] m1, double[][] m2) {
        double[][] result = new double[m1.length][];
        for (int i = 0; i < m1.length; i++) {
            result[i] = new double[m1[i].length];
            for (int j = 0; j < m1[i].length; j++) {
                result[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return result;
    }

    private static double[][] getMatrix(Scanner sc, String matrixName) {
        System.out.printf("Enter size of %smatrix: ", matrixName);
        int n = sc.nextInt();
        int m = sc.nextInt();
        System.out.printf("Enter %smatrix:\n", matrixName);
        double[][] matrix = new double[n][m];
        for (int i = 0; i < n * m; i++) {
            matrix[i / m][i % m] = sc.nextDouble();
        }
        return matrix;
    }
}
