import java.util.*;
import java.io.*;

/*
Issue: 
How to let all sticks parts back to wood with same lengths? -> start from longest sticks and find total can divide max
Idea:
1. wood's length must >= longest sticks && wood's length is divisible by total, that guarantee all woods at the same length

More Efficent, think more conditions:
1.
*/

public class Sticks {
    static boolean[] used;
    static int num;
    static int[] stick;
    static int sum;
    static int max;
    static int numWood;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while ((num = sc.nextInt()) != 0) {
            used = new boolean[num];
            sum = 0;
            stick = new int[num];
            for (int i = 0; i < num; i++) {
                stick[i] = sc.nextInt();
                sum += stick[i];
            }

            Arrays.sort(stick); // sort stick in ascending order
            max = stick[num - 1];

            // wood'length >= longest stick, start from longest stick, update max
            for (int i = max; i <= sum; i++) {
                if (sum % i != 0) {
                    continue;
                }
                max = i;
                // wood's length must be divisible by total
                if (sum % max == 0) {
                    numWood = sum / max; // number of wood
                    if (search(0, num - 1, 0)) {
                        System.out.println(max);
                        break;
                    }
                }
            }
        }
        sc.close();
    }

    // search stick can do combination
    public static boolean search(int res, int next, int numberOfUsedSticks) {
        // this time combination done
        // !!must execute first
        if (res == max) {
            numberOfUsedSticks++;
            res = 0; // check other sticks for next wood
            next = num - 1;
        }

        // done all combination
        if (numberOfUsedSticks == numWood) {
            return true;
        }

        // keep searching for combination
        while (next >= 0) {
            // check stick is used or not
            if (used[next] == false) {
                // curr wood's length + current stick less than max, can put into consideration
                if (res + stick[next] <= max) {
                    used[next] = true;
                    // keep deeper searching
                    if (search(res + stick[next], next - 1, numberOfUsedSticks)) {
                        return true;
                    }

                    // fail, backtrack
                    used[next] = false;

                    /*
                     * Do pruning when fail to do current combination
                     */
                    // when fail to search, res = 0 means curren longest stick can make wood, must
                    // fail to search later
                    if (res == 0) {
                        break;
                    }
                    // can make wood, but rest of them cannot make wood
                    if (res + stick[next] == max) {
                        break;
                    }

                }
            }
            next--; // when used[next] == false not satisfy, point to next stick
        }
        return false;
    }
}