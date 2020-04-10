import java.util.*;
import java.io.*;

public class Cross {
    /*
     * Idea
     * 
     * 1.read file, and know when start new puzzle
     * 
     * 2.numbering base on cross and down invalid word
     * 
     */

    public static void main(String[] args) throws Exception {
        // read file and store matrix to list
        List<String> puzzles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            while (br.ready()) {
                puzzles.add(br.readLine());
            }
        }

        handlePuzzles(puzzles);
    }

    public static void handlePuzzles(List<String> puzzles) {
        int puzzleNo = 0;
        for (int i = 0; i < puzzles.size(); i++) {
            String rowCheck = puzzles.get(i);
            // System.out.println("check row/colum " + rowCheck);
            if (rowCheck.equals("0")) { // terminate program
                break;
            } else if (Character.isDigit(rowCheck.charAt(0))) { // this row shows row and column for new puzzle
                ////////////// get row and column for each puzzle
                puzzleNo++;
                boolean space = false;
                int index = 0;
                String row_str = "";
                String column_str = "";
                while (index < rowCheck.length()) {
                    if (rowCheck.charAt(index) != ' ') {
                        if (!space) {
                            row_str += rowCheck.charAt(index);
                        } else {
                            column_str += rowCheck.charAt(index);
                        }

                    } else {
                        space = true;
                    }
                    index++;
                }

                int row = Integer.valueOf(row_str);
                int column = Integer.valueOf(column_str);
                // System.out.println(row + " " + column);
                //////////////

                /////////////// get puzzle matrix
                System.out.println("Puzzle #" + puzzleNo + ": ");
                // create puzzle matrix
                i++;
                char[][] matrix = new char[row][column];
                for (int rowIndex = 0; rowIndex < row; rowIndex++) {
                    char[] line = puzzles.get(i).toCharArray();

                    for (int cc = 0; cc < line.length; cc++) {
                        matrix[rowIndex][cc] = (char) (line[cc]);
                    }
                    i++;
                }
                // System.out.println(Arrays.deepToString(matrix));
                char[][] copy = deepcopy(matrix);
                ///////////////

                int M = matrix.length;
                int N = matrix[0].length;

                boolean[][] visited_cross = new boolean[M][N];
                boolean[][] visited_down = new boolean[M][N];

                // store result separately
                List<String> acrossRes = new ArrayList<>();
                List<String> downRes = new ArrayList<>();

                int cross = 0;
                int down = 0;

                for (int r = 0; r < M; r++) {
                    for (int c = 0; c < N; c++) {
                        if (visited_cross[r][c]) {
                            cross++;
                        }
                        if (visited_down[r][c]) {
                            down++;
                        }
                        if (visited_cross[r][c] && visited_down[r][c]) {
                            cross--;
                            down--;
                        }

                        if (matrix[r][c] != '*' && !visited_cross[r][c]) {
                            cross++;
                            cross_search(matrix, r, c, visited_cross, cross, acrossRes);
                        }
                        if (copy[r][c] != '*' && !visited_down[r][c]) {
                            down++;
                            down_search(copy, r, c, visited_down, down, downRes);
                        }

                    }
                }

                System.out.println("Across");
                for (int a = 0; a < acrossRes.size(); a++) {
                    System.out.println(acrossRes.get(a));
                }

                System.out.println("Down");
                for (int a = 0; a < downRes.size(); a++) {
                    System.out.println(downRes.get(a));
                }

                i--; // go back last row
            }
        }
    }

    public static void cross_search(char[][] matrix, int r, int c, boolean[][] visited_cross, int cross,
            List<String> res) {
        String word = "";
        for (int j = c; j < matrix[0].length; j++) {
            if (matrix[r][j] != '*' && !visited_cross[r][j]) {
                word += matrix[r][j];
                visited_cross[r][j] = true;
                matrix[r][j] = '*';

                if (j == matrix[0].length - 1) { // last column and it is valid
                    String result = cross + ": " + word;
                    res.add(result);
                }
            } else {
                String result = cross + ": " + word;
                res.add(result);
                break;
            }

        }
    }

    public static void down_search(char[][] matrix, int r, int c, boolean[][] visited_down, int down,
            List<String> res) {
        String word = "";
        for (int i = r; i < matrix.length; i++) {
            if (matrix[i][c] != '*' && !visited_down[i][c]) {
                word += matrix[i][c];
                visited_down[i][c] = true;
                matrix[i][c] = '*';

                if (i == matrix.length - 1) { // last column and it is valid
                    String result = down + ": " + word;
                    res.add(result);
                }
            } else {
                String result = down + ": " + word;
                res.add(result);
                break;
            }
        }

    }

    // clone is shallow copy
    public static char[][] deepcopy(char[][] matrix) {
        char[][] copy = new char[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }
}