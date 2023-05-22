public class WordNode {
    
    public String word = null;
    public int[] frequency = null;
    public int group = -1;

    WordNode next = null;

    // First implementation uses an array to store frequencies, may switch to hash table
    WordNode(String word, int[] frequency) {
        this.word = word;
        this.frequency = frequency;
    }
}
