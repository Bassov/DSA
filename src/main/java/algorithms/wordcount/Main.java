package algorithms.wordcount;

public class Main {

    public static void main(String[] args) {
        WordCount wc = new WordCount();
        FileHelper fh = new FileHelper();
        try {
            fh.write(wc.count("input.txt"), "output.txt");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
