import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnagramThread implements Runnable {

    CopyOnWriteArrayList<String> wordList = null;
    ConcurrentHashMap<Integer, ArrayList<String>> map = null;
    String word = null;

    AnagramThread(
        CopyOnWriteArrayList<String> wordList, 
        ConcurrentHashMap<Integer, ArrayList<String>> map) {
        this.wordList = wordList;
        this.map = map;
    }

    @Override
    public void run() {
        int[] frequency = Anagram.getFrequency(word);
        int hash = Arrays.hashCode(frequency);

        if (map.containsKey(hash)) {
            ArrayList<String> array = map.get(hash);
            array.add(word);
        } else {
            ArrayList<String> array = new ArrayList<>();
            array.add(word);
            map.put(hash, array);
        }
    }
    
    public void setWord(String word) {
        this.word = word;
        // System.out.println(word);
    }
}
