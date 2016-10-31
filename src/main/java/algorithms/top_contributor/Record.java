package algorithms.top_contributor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {

    private String name;
    private int money;
    private LocalDateTime time;

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public Record(String input) {
        parse(input);
    }

    public static LocalDateTime parseTime(String input) {
        return LocalDateTime.parse(input, dateFormatter);
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public LocalDateTime getTime() {
        return time;
    }

    private void parse(String input) {
        String[] arr = input.split(" ");
        name = arr[0] + " " + arr[1].replaceAll("\\W", "");
        money = Integer.parseInt(arr[2]);
        time = parseTime(arr[4]);
    }

}
