package algorithms.knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        File file = new File("input.txt");
        int[] weights = null;
        int[] values = null;
        int weight = 0;

        try {
            Scanner sc = new Scanner(file);
            weights = Stream.of(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            values = Stream.of(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            weight = Integer.parseInt(sc.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Knapsack ks = new Knapsack();

        FileHelper fh = new FileHelper();
        fh.write(ks.knapsack(weights, values, weight), "output.txt");
    }

}

