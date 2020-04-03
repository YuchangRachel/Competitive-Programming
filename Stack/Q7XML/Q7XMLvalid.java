import java.util.*;
import java.io.*;
/*
Write a program which validates an XML file. An XML file is valid if for every opening tag, there is a matching closing tag.
Input: user prompt, type file name
Output: return true or false depending on the outcome of the validation
*/

class Q7XMLvalid {
    public static void main(String[] args) throws Exception {
        System.out.println("Enter XML file: ");
        Scanner sc = new Scanner(System.in);
        String xml = sc.nextLine();
        File xmlFile = new File(xml);
        Reader fileReader = new FileReader(xmlFile);
        BufferedReader buffer = new BufferedReader(fileReader);
        StringBuilder sb = new StringBuilder();

        String line = buffer.readLine();
        while (line != null) {
            sb.append(line);
            line = buffer.readLine();
        }
        String xmlString = sb.toString().trim();
        // System.out.println(xmlString);
        System.out.println(isValidXML(xmlString));

        sc.close();
        buffer.close();
    }

    public static boolean isValidXML(String s) {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '<') {
                if (s.charAt(i + 1) != '/') {
                    String name = "";
                    i++;
                    while (s.charAt(i) != '>') {
                        name += s.charAt(i);
                        i++;
                    }
                    stack.push(name);
                } else {
                    String close_name = "";
                    i += 2;
                    while (s.charAt(i) != '>') {
                        close_name += s.charAt(i);
                        i++;
                    }
                    String top = stack.peek();
                    if (top.equals(close_name)) {
                        stack.pop();
                    } else {
                        return false;
                    }

                }
            }
        }
        // System.out.println(stack);
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }
}