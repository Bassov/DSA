package algorithms.median;

import algorithms.sorting.Insertion;

public class MedianFilter {

    public int[] filter(int[] sequence, int window) {
        int[] result = new int[sequence.length];

        // Cormen said that we should use insertion for small arrays
        Insertion sorter = new Insertion();
        final int half = window / 2;
        int size = sequence.length - 1;
        int left = sequence[0];
        int right = sequence[size];

        for (int i = 0; i < sequence.length; i++) {
            Integer[] temp = new Integer[window];

            for (int j = 0; j < window; j++) {
                int index = i - half + j;

                if (index < 0) {
                    temp[j] = left;
                } else if (index > size) {
                    temp[j] = right;
                } else {
                    temp[j] = sequence[index];
                }
            }

            sorter.sort(temp);
            result[i] = temp[half];
        }

        return result;
    }

}
