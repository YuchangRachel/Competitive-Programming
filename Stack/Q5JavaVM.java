import java.util.*;
/*
Many virtual machines, eg. Java VM, are based on the notion of a run-time stack to hold data. This is like a stack of plates where data may only be removed from or added to the top.
Write an interpreter for such stack machine code. In addition to PUSH which always be followed by an unsigned integer, implement ADD, SUB, MUL, DIV and RESULT operations. 
*/

class Q5JavaVM {
    static Stack<Integer> stack = new Stack<Integer>();

    public static void main(String[] args) {
        System.out.println("Enter VM commands: ");
        boolean start = true;
        while (start) {
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            String[] commandPart = command.split(" ");

            switch (commandPart[0]) {
            case "P":
                stack.push(Integer.valueOf(commandPart[1]));
                break;

            case "A":
                if (!stack.isEmpty()) {

                    Integer pop0 = (Integer) Math.abs(stack.pop());
                    Integer pop1 = (Integer) Math.abs(stack.pop());
                    Integer push0 = (Integer) (pop0 + pop1);
                    stack.push(push0);
                    break;
                }

            case "M":
                if (!stack.isEmpty()) {

                    Integer pop0 = (Integer) Math.abs(stack.pop());
                    Integer pop1 = (Integer) Math.abs(stack.pop());
                    Integer push0 = (Integer) (pop0 * pop1);
                    stack.push(push0);
                    break;
                }

            case "D":
                if (!stack.isEmpty()) {

                    Integer pop0 = (Integer) Math.abs(stack.pop());
                    Integer pop1 = (Integer) Math.abs(stack.pop());
                    Integer push0 = (Integer) (pop1 / pop0);
                    stack.push(push0);
                    break;
                }

            case "S":
                if (!stack.isEmpty()) {

                    Integer pop0 = (Integer) Math.abs(stack.pop());
                    Integer pop1 = (Integer) Math.abs(stack.pop());
                    Integer push0 = (Integer) (pop1 - pop0);
                    stack.push(push0);
                    break;
                }

            case "R":
                start = false;
                sc.close();
                System.out.println(stack.peek());
                System.exit(0);
                break;

            default:
                System.out.println("Invalid input.Try again!");
                break;

            }

        }

    }

}