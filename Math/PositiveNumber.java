import java.util.*;

class PositiveNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String data = sc.nextLine();

        int N = Integer.valueOf(data);
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < N; i++) {
            int sum = 0;
            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0) {
                    sum += j;
                }
            }
            if (sum == i) {
                result.add(i);
            }
        }

        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + " ");
        }

        if (result.size() == 0) {
            System.out.println("No special numbers found");
        }
    }
}