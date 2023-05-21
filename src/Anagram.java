import java.util.Arrays;

public class Anagram {

    public static final int alphalen = 26;
    
    /**
     * 
     * @param str   String
     * @return      int[]
     */
    public static int[] getFrequency(String str) {
        if (str == null) return null;

        int[] set = new int[alphalen];
        Arrays.fill(set, 0);

        for (int i = 0; i < str.length(); ++i) {
            if (!Character.isLetter(str.charAt(i))) continue;
            set[Character.toLowerCase(str.charAt(i)) - 'a']++;
        }

        return set;
    }

    /**
     * 
     * @param set1  int[]
     * @param set2  int[]
     * @return      boolean
     */
    public static boolean frequenciesMatch(int[] set1, int[] set2) {
        if (set1 == null || set2 == null) return false;
        for (int i = 0; i < alphalen; ++i) {
            if (set1[i] != set2[i]) return false;
        }

        return true;
    }
    
    /**
     * First implementation to check if two strings are anagrams, counting letters only.
     * 
     * Precondition: str1 and str2 are strings
     * 
     * Possible optimization: change the precondition so the function takes not strins, but
     * arrays or dictionaries containing the letter frequency of the original string
     * 
     * TODO: Split function into frequency arrays and contrasting them
     * 
     * @param str1  String
     * @param str2  String
     * @return      Boolean
     */
    public static boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false; 

        int[] set1 = new int[alphalen];
        int[] set2 = new int[alphalen];

        Arrays.fill(set1, 0);
        Arrays.fill(set2, 0);

        for (int i = 0; i < str1.length(); ++i) {
            if (!Character.isLetter(str1.charAt(i))) continue;
            set1[Character.toLowerCase(str1.charAt(i)) - 'a']++;
        }

        for (int i = 0; i < str2.length(); ++i) {
            if (!Character.isLetter(str2.charAt(i))) continue;
            set2[Character.toLowerCase(str2.charAt(i)) - 'a']++;
        }

        for (int i = 0; i < alphalen; ++i) {
            if (set1[i] != set2[i]) return false;
        }

        return true;
    }
}
