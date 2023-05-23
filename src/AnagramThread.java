import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AnagramThread implements Runnable {

    WordList list;
    WordNode startingNode;

    ArrayList<String> anagrams;
    ArrayList<int[]> frequencies;

    FileWriter writer;
    AtomicInteger atomic;
    int counter;

    AnagramThread(WordList list, FileWriter writer, AtomicInteger atomic) {
        this.anagrams = new ArrayList<String>();
        this.frequencies = new ArrayList<int[]>();
        
        this.writer = writer;
        this.list = list;
        this.atomic = atomic;
    }

    @Override
    public void run() {
        if (startingNode == null || startingNode.next == null) return;
        // System.out.println(startingNode.word + "; " + startingNode.next.word);
        counter = -1;

        WordNode curr = startingNode.next;
        while (curr != null) {
            if (curr.group != -1) {
                curr = curr.next;
                continue;
            }

            if (!Anagram.frequenciesMatch(startingNode.frequency, curr.frequency)) {
                curr = curr.next;
                continue;
            }

            if (counter == -1) counter = atomic.incrementAndGet();
            // System.out.println(counter);
            startingNode.group = counter;
            anagrams.add(curr.word);
            frequencies.add(curr.frequency);

            curr.group = counter;
            curr = curr.next;
        }

        if (anagrams.size() < 2 || frequencies.size() < 2) return;
        System.out.println(anagrams.toString());
        System.out.println(frequencies.size());
        for (int[] frequency : frequencies) {
            // System.out.println(i + " " + frequencies.size());
            for (int i = 0; i < Anagram.alphalen; ++i)
                System.out.printf("%d ", frequency[i]);
            System.out.println();
        }
        System.out.println();
        // write to out file
        synchronized (writer) {
            try {
                for (String anagram : anagrams) {
                    writer.write(anagram + "\n");       
                }
                writer.write("\n");
            } catch (IOException e) {
                System.out.println("Error ocurred writing to writer object");
                e.printStackTrace();
            }
        }
    }
    
    public void setNode(WordNode startingNode) {
        this.startingNode = startingNode;
        anagrams.clear();
        frequencies.clear();

        anagrams.add(startingNode.word);
        frequencies.add(startingNode.frequency);
    }
}
