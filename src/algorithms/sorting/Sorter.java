package algorithms.sorting;
import java.util.Comparator;

public interface Sorter {

    void sort(Comparable[] toSort);
    void sort(String[] toSort, Comparator comp);

}
