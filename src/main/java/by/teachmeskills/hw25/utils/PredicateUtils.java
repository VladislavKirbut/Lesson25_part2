package by.teachmeskills.hw25.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public final class PredicateUtils {

    private PredicateUtils() {
    }

    public static <T> Predicate<T> applyFilters(List<Predicate<T>> filters) {
        Predicate<T> finalPredicate = null;
        if (!filters.isEmpty()) {
            Iterator<Predicate<T>> iterator = filters.iterator();
            finalPredicate = iterator.next();
            while (iterator.hasNext()) {
                finalPredicate = finalPredicate.and(iterator.next());
            }
        }
        return finalPredicate;
    }
}
