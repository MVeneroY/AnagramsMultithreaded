import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    
    /**
     * Multithreaded section--------------------------------
     * for every word:
     *      get frequency
     *      if it matches an element of the frequency array:
     *              add the word to the element
     * -----------------------------------------------------
     */
    
    static final int n_threads = 4;
    static AnagramThread[] threads;

    public static void main(String[] args) {

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

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(n_threads);
        // Replace with multithreaded section
        // for (String word : wordList) {
        //     int[] frequency = Anagram.getFrequency(word);
        //     int hash = Arrays.hashCode(frequency);

        //     if (map.containsKey(hash)) {
        //         ArrayList<String> array = map.get(hash);
        //         array.add(word);
        //     } else {
        //         ArrayList<String> array = new ArrayList<String>();
        //         array.add(word);
        //         map.put(hash, array);
        //     }
        // }
        // Replace with multithreaded section

        TreeMap<String, ArrayList<String>> sorted = new TreeMap<>();
        for (ArrayList<String> array : map.values()) {
            if (array.size() < 2) continue;
            sorted.put(array.get(0), array);
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter("../output/out.txt");    
            for (ArrayList<String> array : sorted.values()) {
                for (String word : array) writer.write(word + "\n");
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error ocurred with the writer object");
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.printf("Total time: %d ms\n", end - start);
    }    
}