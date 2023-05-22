import java.util.concurrent.atomic.AtomicInteger;

public class AnagramThread implements Runnable {

    WordList list;
    WordNode startingNode;
    AtomicInteger atomic;
    int counter;

    AnagramThread(WordList list, AtomicInteger atomic) {
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
            if (curr.group != -1 || !Anagram.frequenciesMatch(startingNode.frequency, curr.frequency)) {
                curr = curr.next;
                continue;
            }

            if (counter == -1) counter = atomic.incrementAndGet();
            System.out.println(counter);
            startingNode.group = counter;
            curr.group = counter;
            curr = curr.next;
        }
    }
    
    public void setNode(WordNode startingNode) {
        this.startingNode = startingNode;
    }
}
