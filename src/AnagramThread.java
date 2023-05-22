import java.util.concurrent.atomic.AtomicInteger;

public class AnagramThread implements Runnable {

    WordList list;
    AtomicInteger atomic;
    int counter;

    AnagramThread(WordList list, AtomicInteger atomic) {
        this.list = list;
        this.atomic = atomic;
    }

    @Override
    public void run() {
        // do this if anagram is found
        counter = atomic.getAndIncrement();
        System.out.println(counter);
    }
    
}
