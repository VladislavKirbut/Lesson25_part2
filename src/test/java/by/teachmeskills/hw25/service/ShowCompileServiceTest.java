package by.teachmeskills.hw25.service;

import by.teachmeskills.hw25.models.Movie;
import by.teachmeskills.hw25.models.Series;
import by.teachmeskills.hw25.models.Show;
import by.teachmeskills.hw25.repository.RepositoryFileShow;
import by.teachmeskills.hw25.repository.RepositoryShow;
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

    final List<Show> SHOW_LIST = new ArrayList<>(List.of(
            new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
            new Movie("Список Шиндлера", Year.of(1999), "US", 8.8, 473203),
            new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517)
    ));

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(RepositoryFileShow.class);
        service = new ShowCompileService(repo);
    }

    @Test
    void actualShouldBeEqualToExpectedIfSetNull() {
        Mockito.when(repo.getAll()).thenReturn(SHOW_LIST);

        List<Show> actual = service.getShowList(null, null);

        assertEquals(SHOW_LIST, actual);
    }

    @Test
    void shouldBeExceptionIfListFromRepoIsEmpty() {
        List<Show> actual = List.of();
        Mockito.when(repo.getAll()).thenReturn(actual);
        Comparator<Show> comparator = Show.BY_RATING;

        assertThrows(
                UnsupportedOperationException.class,
                () -> service.getShowList(comparator, null)
        );
    }

    @Test
    void shouldBeFilteredByCountryCodeAndSortedByDirectRating() {
        Mockito.when(repo.getAll()).thenReturn(SHOW_LIST);

        Comparator<Show> sorting = Show.BY_RATING;
        Predicate<Show> filter = Show.countryCodeContains("US");
        List<Show> actual = service.getShowList(sorting, filter);

        List<Show> expected = List.of(
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Movie("Список Шиндлера", Year.of(1999), "US", 8.8, 473203)
        );

        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFilteredByRatingAndReverseSortedByReleaseYear() {
        Mockito.when(repo.getAll()).thenReturn(SHOW_LIST);

        Comparator<Show> sorting = Show.BY_RELEASE_YEAR.reversed();
        Predicate<Show> filters = Show.ratingContains(8.9, 10.0);
        List<Show> actual = service.getShowList(sorting, filters);

        List<Show> expected = List.of(
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148)
        );

        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFilteredByRatingIfSortingIsNull() {
        Mockito.when(repo.getAll()).thenReturn(SHOW_LIST);

        Predicate<Show> filters = Show.ratingContains(8.8, 8.8);
        List<Show> actual = service.getShowList(null, filters);

        List<Show> expected = List.of(
                new Movie("Список Шиндлера", Year.of(1999), "US", 8.8, 473203)
        );

        assertEquals(expected, actual);
    }

    @Test
    void shouldBeSortedByReverseRatingIfFilterIsNull() {
        Mockito.when(repo.getAll()).thenReturn(SHOW_LIST);

        Comparator<Show> sorting = Show.BY_RATING.reversed();
        List<Show> actual = service.getShowList(sorting, null);

        List<Show> expected = List.of(
                new Movie("Список Шиндлера", Year.of(1999), "US", 8.8, 473203),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148)
        );
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFilteredByCountryCodeAndByRating() {
        Mockito.when(repo.getAll()).thenReturn(SHOW_LIST);

        Comparator<Show> sorting = Show.BY_RATING;
        Predicate<Show> filters = Show.countryCodeContains("US").and(Show.ratingContains(8.8, 8.8));
        List<Show> actual = service.getShowList(sorting, filters);

        List<Show> expected = List.of(
                new Movie("Список Шиндлера", Year.of(1999), "US", 8.8, 473203)
        );
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeSortedReverseByReleaseYearAndDirectByRating() {
        Mockito.when(repo.getAll()).thenReturn(SHOW_LIST);

        Comparator<Show> sorting = Show.BY_RELEASE_YEAR.thenComparing(Show.BY_RATING);
        Predicate<Show> filters = null;
        List<Show> actual = service.getShowList(sorting,filters);

        List<Show> expected = List.of(
                new Movie("Список Шиндлера", Year.of(1999), "US", 8.8, 473203),
                new Movie("Зеленая миля", Year.of(1999), "US", 9.1, 127148),
                new Series("Шерлок", Year.of(2010), Year.of(2017), "GB", 4, 15, 8.9, 577517)
        );

        assertEquals(expected, actual);
    }
}