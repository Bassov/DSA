package algorithms.char_count;

import collection.tree.AVLTree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class CharCount {

    private AVLTree<Character, Integer> charCounts = new AVLTree<>();

    public void count(String path) throws IOException {
        Path file = Paths.get(path);
        Files.lines(file)
                .map(s -> s.toLowerCase().chars())
                .flatMap(intStream -> intStream.mapToObj(n -> (char)n))
                .forEach(count());
    }

    public void write(String fileName) {
        removeGarbage();
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            charCounts.traverse((key, value) -> writer.print(key + ":" + value + " "), '0', 'z');
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Consumer<Character> count() {
        return character -> {
            if (charCounts.get(character) == null) {
                charCounts.put(character, 1);
            } else {
                charCounts.put(character, (charCounts.get(character) + 1));
            }
        };
    }

    private void removeGarbage() {
        for (char i = ':'; i < 'a'; i++) {
            charCounts.remove(i);
        }
    }

}
