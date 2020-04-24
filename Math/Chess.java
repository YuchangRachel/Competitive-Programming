import java.util.*;
import java.io.*;

public class Chess {
    public static void main(String[] args) throws Exception {
        // read file and store matrix to list
        List<String> chess = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            while (br.ready()) {
                chess.add(br.readLine());
            }
        }

        handleChess(chess);
    }

    public static void handleChess(List<String> chess) {
        for (int i = 0; i < chess.size(); i++) {
            String[] rowArr = chess.get(i).split(" ");
            if (Character.isLetter(rowArr[0].charAt(0))) { // this row shows row and column for new chess
                int m = Integer.valueOf(rowArr[1]);
                int n = Integer.valueOf(rowArr[2]);

                if (rowArr[0].equals("K")) { // KING
                    int num = (int) (Math.ceil((double) (m / 2)) * Math.ceil((double) (n / 2)));
                    System.out.println(num);
                } else if (rowArr[0].equals("Q") || rowArr[0].equals("r")) { // queen rook
                    int num = Math.min(m, n);
                    System.out.println(num);
                } else if (rowArr[0].equals("k")) { // knight
                    int num = (m * n + 1) / 2;
                    System.out.println(num);
                }

            }
        }
    }
}