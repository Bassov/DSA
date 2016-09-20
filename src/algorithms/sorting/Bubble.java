package algorithms.sorting;

import java.util.Comparator;

public class Bubble implements Sorter{

    @Override
    public void sort(Comparable[] toSort) {
        boolean sorted = false;
        Comparable temp;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < toSort.length - 1; i++) {
                if (toSort[i].compareTo(toSort[i + 1]) > 0) {
                    temp = toSort[i + 1];

                    toSort[i + 1] = toSort[i];
                    toSort[i] = temp;
                    sorted = false;
                }
            }
        }
    }

    @Override
    public void sort(String[] toSort, Comparator comp) {
        boolean sorted = false;
        String temp;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < toSort.length - 1; i++) {
                if (comp.compare(toSort[i], toSort[i + 1]) > 0) {
                    temp = toSort[i + 1];

                    toSort[i + 1] = toSort[i];
                    toSort[i] = temp;
                    sorted = false;
                }
            }
        }
    }

}
