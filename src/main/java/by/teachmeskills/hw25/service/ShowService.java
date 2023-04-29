package by.teachmeskills.hw25.service;

import by.teachmeskills.hw25.models.Show;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface ShowService {
    List<Show> getShowList(Comparator<Show> sorting, Predicate<Show> filter);
}
