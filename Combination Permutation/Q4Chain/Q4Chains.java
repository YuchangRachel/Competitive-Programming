import java.util.*;

/*
In the end, all words combinded to big string, otherwise "Impossible".

user insert input!! 
carptent thread rachetc
carpenter thread ratchet

Idea:
1step read user input, delimiter " "
2step find permutation for all possibilities put to List<List>
3step from possibilities, find valid List who element's tail is next element's head
4step concatenate elements in valid list, convert to string, meanwhile, uppercase for first letter of each word, and delete last letter for connected word
5step 
*/
class Q4Chains {
    public static void main(String[] args) {
        // read input
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] input = s.split(" ");

        List<List<String>> result = new ArrayList<>();
        // put permutation result into list's list
        permutation(input, result, new ArrayList<>());

        // find index of valid list in lists
        int validIndex = findValidList(result);

        // only keep validIndex group, validIndex is last-updated so only need 1 valid
        List<String> validGroup = result.get(validIndex);
        // System.out.println("test:" + Arrays.toString(valid.toArray()));

        // handle chain
        String[] resArr = new String[validGroup.size()];
        handleChain(validGroup, resArr);

        // combine array to big string
        StringBuilder resStr = new StringBuilder();
        for (int i = 0; i < resArr.length; i++) {
            resStr.append(resArr[i]);
        }

        System.out.println("Output: " + resStr.toString());

        sc.close();

    }

    // handle chain
    private static void handleChain(List<String> validGroup, String[] resArr) {
        for (int i = 0; i < validGroup.size(); i++) {
            // String library uppercase, stringbuilder hasn't
            // uppercase first char in each word
            String single = validGroup.get(i);
            StringBuilder sb = new StringBuilder(single);
            char first = Character.toUpperCase(single.charAt(0));
            sb.deleteCharAt(0);
            sb.insert(0, first);

            // remove extra last char
            if (i != validGroup.size() - 1) {
                sb.deleteCharAt(sb.length() - 1);
            }

            resArr[i] = sb.toString();
        }
    }

    // find valid list
    private static int findValidList(List<List<String>> result) {
        int validIndex = -1;

        for (int i = 0; i < result.size(); i++) {
            boolean isValidGroup = false;
            for (int j = 1; j < result.get(i).size(); j++) {
                char firstCharSecondWord = result.get(i).get(j).charAt(0);
                char lastCharFirstWord = result.get(i).get(j - 1).charAt(result.get(i).get(j - 1).length() - 1);
                if (firstCharSecondWord != lastCharFirstWord) {
                    isValidGroup = false;
                    break;
                } else {
                    isValidGroup = true;
                }
            }

            if (isValidGroup) { // find chains
                validIndex = i;
            }
        }
        return validIndex;
    }

    // permutation
    private static void permutation(String[] input, List<List<String>> res, List<String> item) {
        if (item.size() == input.length) {
            res.add(new ArrayList<String>(item));
            return;
        }
        for (int i = 0; i < input.length; i++) {
            if (!item.contains(input[i])) {
                item.add(input[i]);
                permutation(input, res, item);
                item.remove(item.size() - 1);
            }
        }
    }

}
