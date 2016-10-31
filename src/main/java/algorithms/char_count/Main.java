package algorithms.char_count;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        CharCount count = new CharCount();
        count.count("input.txt");
        count.write("output.txt");
    }

}
