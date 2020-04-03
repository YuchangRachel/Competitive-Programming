
/*
 * Title: R-combination with replacement/repetition input: file n r : 2 2 output
 * in file: n=[1,2] 11,12 same as 21, 22 Combination: each element can be chosen
 * in 'n' ways, so 'r' elements can be chosen in nxnx ... n = nr times.
 * 
 * refer: https://www.mathsisfun.com/combinatorics/combinations-permutations.html
 * Permutation: order has matter
 * 1) with repetition: n^ r
 * 2) without repetition: n! / (n-r)!
 * (3,2) -> 1 2, 1 3, 2 1, 2 3, 3 1, 3 2
 * 
 * Combination: order no matter ->
 * 1) without repetition
 * Formula: n! / r! (n-r)!
 * For example, let us say balls 1, 2 and 3 are chosen. These are the possibilites:
Order does matter	Order doesn't matter
1 2 3
1 3 2
2 1 3
2 3 1
3 1 2
3 2 1	                    1 2 3
So, the permutations have 6 times as many possibilites.
 * 2) with repetition
 */
import java.util.*;
import java.io.*;

public class Q3RCombination {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("combination.txt"));
        String data = "";
        while (sc.hasNextLine()) {
            data = sc.nextLine();
        }
        String[] input = data.split(" ");
        int n = Integer.parseInt(input[0]);
        int r = Integer.parseInt(input[1]);

        // corner case
        if (n <= 0 || r > n) {
            System.out.println("No Combination Provided!");
        }

        // create input list for number range 1-n
        int[] num = new int[n];
        for (int i = 0; i < num.length; i++) {
            num[i] = i + 1;
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        driver(num, result, r);
        printResult(result);

        // convert list to string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            ArrayList<Integer> inner = result.get(i);
            for (int j = 0; j < inner.size(); j++) {
                sb.append(inner.get(j) + " ");
            }
            sb.append("\n");
        }

        // write to new file
        FileWriter fw = new FileWriter("combinationoutput.txt");
        PrintWriter pw = new PrintWriter(fw);
        pw.print(sb.toString());

        sc.close();
        pw.close();

    }

    private static void printResult(ArrayList<ArrayList<Integer>> res) {
        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i));
        }
        System.out.println("Unique Combination: " + res.size());
    }

    private static void combinationwithRepetition(int[] num, ArrayList<ArrayList<Integer>> result, int r, int layer,
            int start, int[] slot) {
        if (layer == r) {
            ArrayList<Integer> item = new ArrayList<>();
            for (int i = 0; i < slot.length; i++) {
                item.add(num[slot[i]]);
            }
            result.add(item);
            return;
        }
        for (int i = start; i < num.length; i++) {
            slot[layer] = i;
            combinationwithRepetition(num, result, r, layer + 1, i, slot);
        }
    }

    private static void driver(int[] num, ArrayList<ArrayList<Integer>> result, int r) {
        int[] slot = new int[r];
        int layer = 0;
        int startOflist = 0;
        combinationwithRepetition(num, result, r, layer, startOflist, slot);
    }

}