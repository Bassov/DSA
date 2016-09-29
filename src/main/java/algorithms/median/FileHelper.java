package algorithms.median;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileHelper {

    public void write(int[] input, String fileName) {
        StringBuilder sb = new StringBuilder();
        for (int i : input) {
            sb.append(i).append(" ");
        }

        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println(sb.toString().trim());
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
