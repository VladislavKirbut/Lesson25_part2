package by.teachmeskills.hw25.model;

import by.teachmeskills.hw25.exceptions.IncorrectFormatException;

import java.time.Year;

public abstract class Show {

    private final static int COUNTRY_CODE_MAX_LENGTH = 2;
    private final String nameOfShow;
    private final Year releaseYear;
    private final String countryCode;
    private final double rating;
    private final int ratingsCount;

    public Show(String nameOfShow, Year releaseYear, String countryCode, double rating, int ratingsCount) {
        if (nameOfShow == null || nameOfShow.isEmpty())
            throw new IncorrectFormatException("Incorrect name of show format");
        if (releaseYear.isBefore(Year.of(1)))
            throw new IncorrectFormatException("Incorrect year format");
        if (countryCode.length() != COUNTRY_CODE_MAX_LENGTH)
            throw new IncorrectFormatException("Incorrect country code");
        if (rating > 10.0 || rating < 0.0)
            throw new IncorrectFormatException("Incorrect rating");
        if (ratingsCount < 0)
            throw new IncorrectFormatException("Incorrect ratings count format");

        this.nameOfShow = nameOfShow;
        this.releaseYear = releaseYear;
        this.countryCode = countryCode;
        this.rating = rating;
        this.ratingsCount = ratingsCount;
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

    public int getRatingsCount() {
        return ratingsCount;
    }


}
