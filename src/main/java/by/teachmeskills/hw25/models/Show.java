package by.teachmeskills.hw25.models;

import by.teachmeskills.hw25.exceptions.IncorrectFormatException;

import java.time.Year;

public abstract class Show {

    private final static int COUNTRY_CODE_MAX_LENGTH = 2;
    private final static double MAX_RATING = 10.0;
    private final static double MIN_RATING = 0.0;
    private final static int MIN_COUNT_OF_RATINGS_LENGTH = 0;
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
