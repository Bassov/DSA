import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordCount {

    private static final String[] garbage = {
            "a", "the", "in", "at", "to",
            "on", "not", "for", "'s", "'d",
            "'re", "is", "are", "am",
            "has", "I", "we", "you"
    };

    public String count(String path) {
        File file = new File(path);
        // My HashMap resizes dynamically
        Map<String, Integer> result = new HashMap<>();

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                Thread t = new Thread(new Runnable() {
                    public void run()
                    {
                        Map<String, Integer> temp = countInString(sc.nextLine());
                        mergeMaps(result, temp);
                    }
                });
                t.run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        deleteGarbage(result);
        return findMaxPair(result);
    }

    private void mergeMaps(Map<String, Integer> target, Map<String, Integer> second) {
        for (String str : second.keySet()) {
            int value = second.get(str);
            Integer current = target.get(str);

            if (current == null) {
                target.put(str, value);
            } else {
                target.put(str, current + value);
            }
        }
    }

    private void deleteGarbage(Map<String, Integer> target) {
        for (String str : garbage) {
            target.remove(str);
        }
    }

    private String findMaxPair(Map<String, Integer> target) {
        String maxKey = null;
        int maxVal = 0;
        for (String str : target.keySet()) {
            Integer value = target.get(str);
            if (value > maxVal) {
                maxVal = value;
                maxKey = str;
            }
        }

        return maxKey + " " + maxVal;
    }

    private Map<String, Integer> countInString(String input) {
        Map<String, Integer> result = new HashMap<>();

        String[] line = input.split(" ");
        for (String str : line) {
            String key = str.toLowerCase().replaceAll("\\W", "");
            Integer current = result.get(key);

            if (current == null) {
                result.put(key, 1);
            } else {
                result.put(key, current + 1);
            }
        }
        return result;
    }

}
