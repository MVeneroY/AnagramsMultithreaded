import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
// import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
    
    static final int n_threads = 16;
    static AnagramThread[] threads;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting program");
        threads = new AnagramThread[n_threads];
        ConcurrentHashMap<Integer, ArrayList<String>> map = new ConcurrentHashMap<>();
        ArrayList<String> wordList = new ArrayList<>();
        CopyOnWriteArrayList<String> safeList = null;

        String pathname;
        if (args.length == 0) pathname = "../data/words_alpha.txt";
        else pathname = args[0];

        // System.out.println("lmao?");

        // Obtain words from .txt file and store in linked list
        try {
            File fhandle = new File(pathname);
            Scanner scanner = new Scanner(fhandle);
            System.out.println("Getting words from " + pathname);
            while (scanner.hasNextLine()) {
                final String word = scanner.nextLine();
                wordList.add(word);
                // System.out.println(word);
            }
            System.out.println("Words are loaded");
            safeList = new CopyOnWriteArrayList<>(wordList);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error ocurred.");
            e.printStackTrace();
        } 

        System.out.println("Initializing threads");

        int threadc = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(n_threads);
        for (int i = 0; i < n_threads; ++i) { threads[i] = new AnagramThread(safeList, map); }

        long start = System.currentTimeMillis();
        for (String word : wordList) {
            Thread.sleep(1);
            threads[threadc].setWord(word);
            executorService.execute(threads[threadc++]);

            threadc %= n_threads;
        }
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
            writer = new FileWriter("../output/Multiout.txt");    
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

        System.out.println("Shutting down executor service...");
        executorService.shutdown();
        while (!executorService.isTerminated()) { 
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }    
}