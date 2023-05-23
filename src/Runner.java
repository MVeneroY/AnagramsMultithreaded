import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
class Runner {

    static final int n_threads = 8;

    static AtomicInteger atomic;
    // static WordList list;
    static AnagramThread[] threads;

    public static void main(String[] args) {

        atomic = new AtomicInteger();
        // list = new WordList();
        threads = new AnagramThread[n_threads];
        ConcurrentHashMap<int[], ArrayList<String>> map = new ConcurrentHashMap<int[], ArrayList<String>>();
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

        // Initialize threads
        int threadc = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(n_threads);
        for (int i = 0; i < n_threads; ++i) { threads[i] = new AnagramThread(wordList, writer, atomic); }

        long start = System.currentTimeMillis();
        for (String word : wordList) {
            // try {
            //     Thread.sleep(1);
            // } catch (InterruptedException e) { e.printStackTrace(); }

            // threads[threadc].setNode(curr);
            // executorService.execute(threads[threadc++]);
            // threadc %= n_threads;
            // curr = curr.next;
        }

        // try {
        //     writer.close();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        long end = System.currentTimeMillis();
        System.out.printf("Total time: %d ms\n", end - start);

        System.err.println("Shutting down executor service...");
        executorService.shutdown();
        while (!executorService.isTerminated()) { 
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        // list.display();
    }
}