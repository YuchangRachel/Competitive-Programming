import java.util.*;

public class Classic {

    public static void main(String[] args) {
        /* 1 */
        Stack<Character> stack1 = new Stack<>();
        stack1.push('1');
        stack1.push('2');
        stack1.push('3');
        stack1.push('4');
        System.out.println("Original Stack: " + stack1); // 1 2 3 4
        reverseStack(stack1);
        System.out.println("Reversed Stack: " + stack1); // 4 3 2 1
    }

    /*
     * 1. Reverse order of elements in Stack (No extra O(n) space, Using Recursion)
     */
    private static void reverseStack(Stack<Character> stack1) {
        // pop all elements in stack, then insert at bottom
        if (!stack1.isEmpty()) {
            char top = stack1.peek();
            stack1.pop(); // pop 4 3 2 1 -> empty
            reverseStack(stack1);
            insert_at_bottom(stack1, top); // insert 1 2 3
                                           // stack [] [1] [2,1] [3,2,1]
        } else {
            return;
        }
    }

    // insert one at bottom(empty stack) and push rest of them
    private static void insert_at_bottom(Stack<Character> stack1, char top) {
        if (stack1.isEmpty()) {
            stack1.push(top);
            return;
        } else {
            char x = stack1.peek();
            stack1.pop();
            insert_at_bottom(stack1, top);
            stack1.push(x);
        }
    }

}