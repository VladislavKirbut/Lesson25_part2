package by.teachmeskills.hw25.models;

import by.teachmeskills.hw25.exceptions.IncorrectFormatException;
import java.util.Comparator;
import java.time.Year;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public abstract class Show {

    private final static class ComparatorByReleaseYear implements Comparator<Show> {
        @Override
        public int compare(Show obj1, Show obj2) {
            return obj1.releaseYear.compareTo(obj2.releaseYear);
        }
    }

    private final static class ComparatorByRating implements Comparator<Show> {
        @Override
        public int compare(Show obj1, Show obj2) {
            return Double.compare(obj1.rating, obj2.rating);
        }
    }

    public final static class PredicateByCountryCode implements Predicate<Show> {

        private final Pattern pattern;
        private PredicateByCountryCode(String query) {
            pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE |
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.UNICODE_CASE);
        }
        @Override
        public boolean test(Show show) {
            return pattern.matcher(show.getCountryCode()).find();
        }
    }

    private static final class PredicateByRating implements Predicate<Show> {
        private final double minRating;
        private final double maxRating;
        private PredicateByRating(double minRating, double maxRating) {
            if (minRating < MIN_RATING || maxRating > MAX_RATING || minRating > maxRating)
                throw new IncorrectFormatException("Format of arguments is incorrect");

            this.minRating = minRating;
            this.maxRating = maxRating;
        }

        @Override
        public boolean test(Show show) {
            return show.getRating() >= minRating && show.getRating() <= maxRating;
        }
    }

    public static final Comparator<Show> BY_RELEASE_YEAR = new ComparatorByReleaseYear();
    public static final Comparator<Show> BY_RATING = new ComparatorByRating();
    private static final int COUNTRY_CODE_MAX_LENGTH = 2;
    private static final double MAX_RATING = 10.0;
    private static final double MIN_RATING = 0.0;
    private static final int MIN_COUNT_OF_RATINGS_LENGTH = 0;
    protected final String nameOfShow;
    protected final Year releaseYear;
    protected final String countryCode;
    protected final double rating;
    protected final int countOfRatings;

    public Show(String nameOfShow, Year releaseYear, String countryCode, double rating, int countOfRatings) {
        if (nameOfShow.isEmpty())
            throw new IncorrectFormatException("Incorrect name of show format");
        if (countryCode.length() != COUNTRY_CODE_MAX_LENGTH)
            throw new IncorrectFormatException("Incorrect country code");
        if (rating > MAX_RATING || rating < MIN_RATING)
            throw new IncorrectFormatException("Incorrect rating");
        if (countOfRatings < MIN_COUNT_OF_RATINGS_LENGTH)
            throw new IncorrectFormatException("Incorrect ratings count format");

        this.nameOfShow = nameOfShow;
        this.releaseYear = releaseYear;
        this.countryCode = countryCode;
        this.rating = rating;
        this.countOfRatings = countOfRatings;
    }

    public static Predicate<Show> countryCodeContains(String query) {
        return new PredicateByCountryCode(query);
    }

    public static Predicate<Show> ratingContains(double minRating, double maxRating) {
        return new PredicateByRating(minRating, maxRating);
    }

    public String getNameOfShow() {
        return nameOfShow;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public double getRating() {
        return rating;
    }

    public int getCountOfRatings() {
        return countOfRatings;
    }
}
