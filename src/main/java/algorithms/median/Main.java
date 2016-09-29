package algorithms.median;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        File file = new File("input.txt");
        int[] seq = null;
        int window = 0;

        try {
            Scanner sc = new Scanner(file);
            seq = Stream.of(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            window = Integer.parseInt(sc.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        MedianFilter mf = new MedianFilter();
        FileHelper fh = new FileHelper();
        fh.write(mf.filter(seq, window), "output.txt");
    }

}
