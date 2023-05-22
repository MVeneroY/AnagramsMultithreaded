import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class Runner {

    static final int n_threads = 4;

    static AtomicInteger atomic;
    static WordList list;
    static AnagramThread[] threads;

    public static void main(String[] args) {

        atomic = new AtomicInteger();
        list = new WordList();
        threads = new AnagramThread[n_threads];

        // Obtain words from .txt file and store in linked list
        try {
            File fhandle = new File("../data/words_alpha.txt");
            Scanner scanner = new Scanner(fhandle);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                int[] frequency = Anagram.getFrequency(word);
                WordNode node = new WordNode(word, frequency);

                list.add(node);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error ocurred.");
            e.printStackTrace();
        } 

        // Initialize threads
        ExecutorService executorService = Executors.newFixedThreadPool(n_threads);
        for (int i = 0; i < n_threads; ++i) { threads[i] = new AnagramThread(list, atomic); }

        long start = System.currentTimeMillis();
        // While (...) { Search for Anagrams }
        long end = System.currentTimeMillis();

        System.out.printf("Total time: %d ms\n", end - start);

        // list.display();
    }
}