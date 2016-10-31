package algorithms.top_contributor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TopContributor top = new TopContributor();
        top.count("input.txt", "output.txt");
    }

}
