import java.util.*;
import java.io.*;

class FibonacciWord {
    public static void main(String[] args) throws Exception {
        List<String> cases = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("fib.txt"))) {
            while (br.ready()) {
                cases.add(br.readLine());
            }
        }
        handleCases(cases);
    }

    public static void handleCases(List<String> cases) {
        int casesNo = 0;
        for (int i = 0; i < cases.size(); i++) {
            if (i % 2 == 0) { // it is n
                int n = Integer.valueOf(cases.get(i));
                i++;
                String pattern = cases.get(i);
                String fn = fib2(n);
                System.out.println(fn);
                int count = 0;
                count = counter(fn, pattern);
                System.out.println("Case " + ++casesNo + " : " + count);
            }
        }
    }

    // counter occurence of substring in a string
    public static int counter(String res, String pattern) {
        int count = 0;
        for (int i = 0; i <= res.length() - pattern.length(); i++) { // must <= check last pair
            int j = 0;
            for (j = 0; j < pattern.length(); j++) {
                if (res.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }

            if (j == pattern.length()) {
                count++;
            }
        }

        return count;
    }

    // fibonacci recursion
    public static String fib(int n, String res) {
        if (n == 0) {
            res += "0";
            return res;
        }
        if (n == 1) {
            res += "1";
            return res;
        }

        return res += fib(n - 1, res) + fib(n - 2, res);
    }

    // better: bottom-up dp
    public static String fib2(int n) {
        if (n == 0 || n == 1) {
            return n + "";
        }
        String[] memo = new String[n + 1];
        memo[0] = "0";
        memo[1] = "1";
        for (int i = 2; i <= n; i++) {
            memo[i] = memo[i - 1].concat(memo[i - 2]);
        }
        return memo[n];
    }

}