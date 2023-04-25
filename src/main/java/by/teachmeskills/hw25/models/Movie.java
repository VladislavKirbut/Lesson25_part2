package by.teachmeskills.hw25.models;

import java.time.Year;

public class Movie extends Show {
    public Movie(String nameOfShow, Year releaseYear, String countryCode, double rating, int countOfRatings) {
        super(nameOfShow, releaseYear, countryCode, rating, countOfRatings);
    }

    @Override
    public String toString() {
        return "{Movie} %-35s %7s %3s %5s %10d".formatted(nameOfShow, releaseYear, countryCode, rating, countOfRatings);
    }
}
