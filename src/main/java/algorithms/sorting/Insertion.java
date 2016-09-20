package algorithms.sorting;

import java.util.Comparator;

public class Insertion implements Sorter {

    @Override
    public void sort(Comparable[] toSort) {
        Comparable toInsert;

        for (int sorted = 1; sorted < toSort.length; sorted++) {
            toInsert = toSort[sorted];

            int i = sorted - 1;
            while (i >= 0 && toSort[i].compareTo(toInsert) > 0) {
                toSort[i + 1] = toSort[i];
                i--;
            }
            toSort[i + 1] = toInsert;
        }

    }

    @Override
    public void sort(String[] toSort, Comparator comp) {
        String toInsert;

        for (int sorted = 1; sorted < toSort.length; sorted++) {
            toInsert = toSort[sorted];

            int i = sorted - 1;
            while (i >= 0 && comp.compare(toSort[i], toInsert) > 0) {
                toSort[i + 1] = toSort[i];
                i--;
            }
            toSort[i + 1] = toInsert;
        }
    }

}
