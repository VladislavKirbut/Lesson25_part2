package by.teachmeskills.hw25.service;

import by.teachmeskills.hw25.models.Show;
import by.teachmeskills.hw25.repository.RepositoryShow;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ShowCompileService implements ShowService {
    private final RepositoryShow repositoryShow;

    public ShowCompileService(RepositoryShow repositoryShow) {
        Objects.requireNonNull(repositoryShow);

        this.repositoryShow = repositoryShow;
    }

    @Override
    public List<Show> getShowList(List<Comparator<Show>> sorting, List<Predicate<Show>> filters) {
        List<Show> showList = repositoryShow.getAll();
        applyFilters(filters, showList);
        applySorting(sorting, showList);
        return showList;
    }

    public static void applyFilters(List<Predicate<Show>> filters, List<Show> showList) {
        if (!filters.isEmpty()) {
            Iterator<Predicate<Show>> filtersIterator = filters.iterator();
            Predicate<Show> filter = filtersIterator.next();
            while (filtersIterator.hasNext()) {
                Predicate<Show> next = filtersIterator.next();
                filter = filter.and(next);
            }
            showList.removeIf(filter.negate());
        }
    }

    public static void applySorting(List<Comparator<Show>> sortingList, List<Show> showList) {
        if (!sortingList.isEmpty()) {
            Iterator<Comparator<Show>> sortingIterator = sortingList.iterator();
            Comparator<Show> sorting = sortingIterator.next();
            while (sortingIterator.hasNext()) {
                Comparator<Show> next = sortingIterator.next();
                sorting = sorting.thenComparing(next);
            }
            showList.sort(sorting);
        }
    }


}
