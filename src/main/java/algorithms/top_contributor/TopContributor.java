package algorithms.top_contributor;

import collection.queue.PriorityQueue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TopContributor {

    private LocalDateTime start;
    private LocalDateTime end;
    private ArrayList<Record> records = new ArrayList<>();

    private PriorityQueue<Integer, String> top = new PriorityQueue<>();

    public void count(String input, String output) throws IOException {
        read(input);

        try (PrintWriter writer = new PrintWriter(output, "UTF-8")) {
            // we need to save current index to not add records twice
            int i = 0;
            Record record = records.get(i);
            // iterate through dates by 1 our
            for (LocalDateTime current = start; current.compareTo(end) <= 0; current = current.plusHours(1)) {
                // we need to add all records that was before current time
                // but we need to save state to do the job once
                while (record.getTime().compareTo(current) <= 0) {
                    top.put(record.getMoney(), record.getName());
                    if (++i >= records.size()) {
                        break;
                    }
                    record = records.get(i);
                }

                writer.print(current + ": ");
                writer.println(top.peek());
            }

        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void read(String path) throws IOException {
        Path file = Paths.get(path);
        BufferedReader reader = Files.newBufferedReader(file);

        String[] boss = reader.readLine().split(" ");
        // according to input format
        start = Record.parseTime(boss[1]);
        end = Record.parseTime(boss[3]);

        reader.lines().forEach(line -> records.add(new Record(line)));
    }

}
