package by.teachmeskills.hw25.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class ComparatorUtils {

    private ComparatorUtils() {
    }

    public static <T> Comparator<T> applySorting(List<Comparator<T>> sorting) {
        Comparator<T> finalComparator = null;
        if (!sorting.isEmpty()) {
            Iterator<Comparator<T>> iterator = sorting.iterator();
            finalComparator = iterator.next();
            while (iterator.hasNext()) {
                finalComparator = finalComparator.thenComparing(iterator.next());
            }
        }

        return finalComparator;
    }
}
