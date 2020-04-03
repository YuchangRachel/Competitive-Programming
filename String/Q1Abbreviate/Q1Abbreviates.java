import java.io.*;
import java.util.*;

class Q1Abbreviates {
    public static void main(String[] args) throws Exception {
        List<String> res = new ArrayList<>();
        List<String> fileLines = new ArrayList<>(); // store all lines in file
        // read input file's many lines
        Scanner sc = new Scanner(new File("abbreviates.txt"));
        String data = "";
        while (sc.hasNextLine()) {
            data = sc.nextLine();
            fileLines.add(data);
        }
        StringBuilder result = new StringBuilder();
        for (String line : fileLines) {
            result.append(line);
        }
        data = result.toString();
        String[] words = data.split("['|^&*#$\\s]"); // find all delimiters
        for (String word : words) {
            if (word.length() > 4) { // need follow rules to do abbreviate
                char first = word.charAt(0);
                char last = word.charAt(word.length() - 1);
                // handle middle substring
                // remove all vowels
                String middle = removeVowels(word.substring(1, word.length()));
                // must check rule1:length of word
                if (middle.length() < 2) { // loool
                    res.add(word);
                } else {
                    // handle no consecutive consonant address->adrs asssl
                    String newWordNoVowel = first + middle + last;
                    String newWordNoConsecutiveConsonant = removeConsecutiveConsonant(newWordNoVowel);
                    res.add(newWordNoConsecutiveConsonant); // state
                }

            } else {
                res.add(word);
            }
        }
        printList(res);

        sc.close();
    }

    private static void printList(List<String> ls) {
        for (String word : ls) {
            System.out.print(word + " ");
        }
    }

    private static String removeConsecutiveConsonant(String s) {
        // d dddrbsrss
        StringBuilder sb = new StringBuilder();
        char lastSee = s.charAt(0);
        sb.append(lastSee);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != lastSee) { // compare previous char in new string , not compare previous char in old
                                          // string
                sb.append(s.charAt(i));
                lastSee = s.charAt(i);
            }
        }
        return sb.toString();
    }

    private static String removeVowels(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!isVowel(s.charAt(i))) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    private static boolean isVowel(char ch) {
        return (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I'
                || ch == 'O' || ch == 'U');
    }

}