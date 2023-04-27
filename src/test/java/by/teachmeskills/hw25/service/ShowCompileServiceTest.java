package by.teachmeskills.hw25.service;

import by.teachmeskills.hw25.models.Movie;
import by.teachmeskills.hw25.models.Series;
import by.teachmeskills.hw25.models.Show;
import by.teachmeskills.hw25.repository.RepositoryFileShow;
import by.teachmeskills.hw25.repository.RepositoryShow;

import static by.teachmeskills.hw25.service.ShowCompileService.applyFilters;
import static by.teachmeskills.hw25.service.ShowCompileService.applySorting;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ShowCompileServiceTest {

    RepositoryShow repo;
    ShowService service;

    List<Show> showList;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(RepositoryFileShow.class);
        service = new ShowCompileService(repo);
        showList = new ArrayList<>();
        showList.add(new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148));
        showList.add(new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203));
        showList.add(new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517));
        showList.add(new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375));
        showList.add(new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871));
    }

    @Test
    void showListShouldNotBeChangedIfFiltersListEmpty() {
        int sourceListSize = showList.size();

        List<Predicate<Show>> filtersList = new ArrayList<>();
        applyFilters(filtersList, showList);
        assertEquals(sourceListSize, showList.size());
    }

    @Test
    void showListShouldContainsOneShowIfFilterByCountryCode() {
        List<Predicate<Show>> filtersList = List.of(Show.countryCodeContains("GB"));

        List<Show> resultList = List.of(
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517)
        );

        applyFilters(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldContainsTwoShowIfFilterByRating() {
        List<Predicate<Show>> filtersList = List.of(Show.ratingContains(8.9, 10));

        List<Show> resultList =
                List.of(
                        new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                        new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517)
                );

        applyFilters(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldContainsOneShowIfFilterByRatingAndByCountryCode() {
        List<Predicate<Show>> filtersList = List.of(
                Show.ratingContains(8.9, 8.9),
                Show.countryCodeContains("GB")
        );

        List<Show> resultList = List.of(
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517)
        );

        applyFilters(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByReleaseYearIfReverseSortingByReleaseYear() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RELEASE_YEAR.reversed());

        List<Show> resultList = List.of(
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871)
        );

        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByRatingIfReverseSortingByRating() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RATING.reversed());

        List<Show> resultList = List.of(
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871)
        );

        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByRatingIfDirectSortingByRating() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RATING);

        List<Show> resultList = List.of(
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148)
        );

        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByReleaseYearIfDirectSortingByReleaseYear() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RELEASE_YEAR);

        List<Show> resultList = List.of(
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375)
        );

        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByReleaseYearAndRatingIfDirectSortingByReleaseYearAndRating() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RATING, Show.BY_RELEASE_YEAR);

        List<Show> resultList = List.of(
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148)
        );

        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByRatingAndReleaseYearIfReverseSortingByRatingAndReleaseYear() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RATING.reversed(), Show.BY_RELEASE_YEAR.reversed());

        List<Show> resultList = List.of(
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871)
        );

        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByReverseYearAndDirectRating() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RATING, Show.BY_RELEASE_YEAR.reversed());

        List<Show> resultList = List.of(
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148)
        );
        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void showListShouldBeSortByDirectReleaseYearAndReverseByRatingYear() {
        List<Comparator<Show>> filtersList = List.of(Show.BY_RELEASE_YEAR.reversed(), Show.BY_RATING);

        List<Show> resultList = List.of(
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871)
        );

        applySorting(filtersList, showList);
        assertEquals(resultList, showList);
    }

    @Test
    void shouldOnlyGetFilteredList() {
        Mockito.when(repo.getAll()).thenReturn(showList);

        List<Predicate<Show>> filtersList = List.of(
                Show.ratingContains(8.9, 8.9)
        );

        List<Show> resultList = List.of(
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517)
        );

        assertEquals(resultList, service.getShowList(new ArrayList<>(), filtersList));
    }

    @Test
    void shouldOnlyGetSortedList() {
        Mockito.when(repo.getAll()).thenReturn(showList);

        List<Comparator<Show>> sortingList = List.of(Show.BY_RATING);

        List<Show> resultList = List.of(
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148)
        );

        assertEquals(resultList, service.getShowList(sortingList, new ArrayList<>()));
    }

    @Test
    void shouldGetSortedAndFilteredList() {
        Mockito.when(repo.getAll()).thenReturn(showList);

        List<Comparator<Show>> sortingList = List.of(Show.BY_RELEASE_YEAR);
        List<Predicate<Show>> filtersList = List.of(
                Show.countryCodeContains("US")
        );

        List<Show> resultList = List.of(
                new Series("Том и Джерри", Year.of(1940), Year.of(1992), "US", 1, 161, 8.7, 10871),
                new Movie("Список Шиндлера", Year.of(1993), "US", 8.8, 473203),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Series("Чернобыль", Year.of(2019), Year.of(2019), "US", 1, 5, 8.8, 466375)
        );

        assertEquals(resultList, service.getShowList(sortingList, filtersList));
    }
}
