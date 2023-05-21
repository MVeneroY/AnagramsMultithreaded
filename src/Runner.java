import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Runner {

    static WordList list;

    public static void main(String[] args) {

        list = new WordList();

        long start = System.currentTimeMillis();
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
        long end = System.currentTimeMillis();

        System.out.printf("Total time: %d ms\n", end - start);

        // list.display();
    }
}