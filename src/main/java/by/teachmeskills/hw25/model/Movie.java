package by.teachmeskills.hw25.model;

import java.time.Year;

public class Movie extends Show {
    public Movie(String nameOfShow, Year releaseYear, String countryCode, double rating, int countOfRatings) {
        super(nameOfShow, releaseYear, countryCode, rating, countOfRatings);
    }
}
