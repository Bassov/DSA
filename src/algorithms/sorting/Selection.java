package algorithms.sorting;

import java.util.Comparator;

public class Selection implements Sorter {


    @Override
    public void sort(Comparable[] toSort) {
        int last = toSort.length - 1;
        Comparable temp;

        while (last >= 1) {
            int largest = 0;

            for (int i = 0; i <= last; i++) {
                if (toSort[i].compareTo(toSort[largest]) > 0) {
                    largest = i;
                }
            }

            temp = toSort[last];
            toSort[last] = toSort[largest];
            toSort[largest] = temp;
            last--;
        }
    }

    @Override
    public void sort(String[] toSort, Comparator comp) {
        int last = toSort.length - 1;
        String temp;

        while (last >= 1) {
            int largest = 0;

            for (int i = 0; i <= last; i++) {
                if (comp.compare(toSort[i], toSort[largest]) > 0) {
                    largest = i;
                }
            }

            temp = toSort[last];
            toSort[last] = toSort[largest];
            toSort[largest] = temp;
            last--;
        }
    }

}
