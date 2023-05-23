import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreaded {
    
    /**
     * Notes: The current implementation is too slow
     * What if. instead of getting the letter frequencies with a single thread
     * I relayed that to the multithreaded section
     * 
     * define frequency object {frequency, words}
     * create a frequency array (or linked list)
     * 
     * Multithreaded section--------------------------------
     * for every word:
     *      get frequency
     *      if it matches an element of the frequency array:
     *              add the word to the element
     * -----------------------------------------------------
     * 
     * TODO: finalize current implementation. (put anagrams in a .txt file as output)
     * TODO: code implementation specified above and contrast time measurements with current implementation.
     */
    
    static final int n_threads = 8;

    static AtomicInteger atomic;
    // static WordList list;
    static AnagramThread[] threads;

    public static void main(String[] args) {

        atomic = new AtomicInteger();
        threads = new AnagramThread[n_threads];
        ConcurrentHashMap<Integer, ArrayList<String>> map = new ConcurrentHashMap<Integer, ArrayList<String>>();
        ArrayList<String> wordList = new ArrayList<String>();

        String pathname;
        if (args.length == 0) pathname = "../data/words_alpha.txt";
        else pathname = args[0];

        // Obtain words from .txt file and store in linked list
        try {
            File fhandle = new File(pathname);
            Scanner scanner = new Scanner(fhandle);
            while (scanner.hasNextLine()) {
                final String word = scanner.nextLine();
                wordList.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error ocurred.");
            e.printStackTrace();
        } 

        // Initialize file descriptor for output file
        // FileWriter writer = null;
        // try {
        //     writer = new FileWriter("../output/out.txt");
        // } catch (IOException e) {
        //     System.out.println("An error ocurred with the writer object");
        //     e.printStackTrace();
        // }

        long start = System.currentTimeMillis();
        for (String word : wordList) {
            int[] frequency = Anagram.getFrequency(word);
            int hash = Arrays.hashCode(frequency);
            if (map.containsKey(hash)) {
                ArrayList<String> array = map.get(hash);
                array.add(word);
            }

            else {
                ArrayList<String> array = new ArrayList<String>();
                array.add(word);
                map.put(hash, array);
            }
        }

        for (ArrayList<String> array : map.values()) {
            if (array.size() < 2) continue;

            System.out.println(array.toString());
        }

        long end = System.currentTimeMillis();
        System.out.printf("Total time: %d ms\n", end - start);
    }    
}
