package by.teachmeskills.hw25.model;

import by.teachmeskills.hw25.exceptions.IncorrectFormatException;

import java.time.Year;

public class Series extends Show {
    private final static int MIN_COUNT_OF_SEASONS = 1;
    private static final int MIN_COUNT_OF_EPISODES = 1;

    private final Year yearOfLastEpisode;
    private final int countOfSeasons;
    private final int countOfEpisode;

    public Series(String nameOfShow, Year releaseYear, String countryCode, double rating, int countOfRatings,
                  Year yearOfLastEpisode, int countOfSeasons, int countOfEpisode) {
        super(nameOfShow, releaseYear, countryCode, rating, countOfRatings);

        if (yearOfLastEpisode.isBefore(releaseYear))
            throw new IncorrectFormatException("Incorrect year of last episode");
        if (countOfSeasons < MIN_COUNT_OF_SEASONS)
            throw new IncorrectFormatException("Incorrect count of seasons");
        if (countOfEpisode < MIN_COUNT_OF_EPISODES)
            throw new IncorrectFormatException("Incorrect count of episodes");

        this.yearOfLastEpisode = yearOfLastEpisode;
        this.countOfSeasons = countOfSeasons;
        this.countOfEpisode = countOfEpisode;
    }

    public Year getYearOfLastEpisode() {
        return yearOfLastEpisode;
    }

    public int getCountOfSeasons() {
        return countOfSeasons;
    }

    public int getCountOfEpisode() {
        return countOfEpisode;
    }
}
