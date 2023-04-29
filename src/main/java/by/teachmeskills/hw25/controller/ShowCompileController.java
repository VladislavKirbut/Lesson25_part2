package by.teachmeskills.hw25.controller;

import by.teachmeskills.hw25.exceptions.IncorrectFormatException;
import by.teachmeskills.hw25.models.Show;
import by.teachmeskills.hw25.service.ShowService;
import by.teachmeskills.hw25.utils.ComparatorUtils;
import by.teachmeskills.hw25.utils.PredicateUtils;

import java.util.*;
import java.util.function.Predicate;

public class ShowCompileController implements ShowController {
    private static final int MAX_LENGTH_OF_SORTING_COMMAND = 2;
    private static final int MAX_LENGTH_OF_FILTER_COMMAND = 3;
    private final ShowService service;
    private final Scanner scanner = new Scanner(System.in);

    public ShowCompileController(ShowService service) {
        Objects.requireNonNull(service);

        this.service = service;
    }

    public void run() {
        Comparator<Show> sorting = ComparatorUtils.applySorting(getSortingCommandsList());
        Predicate<Show> filters = PredicateUtils.applyFilters(getFilterCommandsList());

        List<Show> show = service.getShowList(sorting, filters);
        if (show.isEmpty())
            System.out.println("Nothing not find");
        else {
            for (Show showLine : show)
                System.out.println(showLine);
        }
    }

    private List<Comparator<Show>> getSortingCommandsList() {
        List<Comparator<Show>> sortingList = new ArrayList<>();

        System.out.println("""
                Choose sorting:
                -> byReleaseYear <reverse|direct>
                -> byRating <reverse|direct>
                -> close
                """);

        while (true) {
            String[] commandParts = scanner.nextLine().split("\\s+");

            if (commandParts.length > MAX_LENGTH_OF_SORTING_COMMAND)
                throw new IncorrectFormatException("Unknown command");

            if (commandParts[0].equals("close"))
                return sortingList;

            Comparator<Show> comparator = switch (commandParts[0]) {
                case "byReleaseYear" -> Show.BY_RELEASE_YEAR;
                case "byRating" -> Show.BY_RATING;
                default -> throw new IncorrectFormatException("Unknown command");
            };

            comparator = switch (commandParts[1]) {
                case "reverse" -> comparator.reversed();
                case "direct" -> comparator;
                default -> throw new IncorrectFormatException("Unknown command");
            };

            sortingList.add(comparator);
        }
    }

    private List<Predicate<Show>> getFilterCommandsList() {
        List<Predicate<Show>> filtersList = new ArrayList<>();

        System.out.println("""
                Choose filter:
                -> byCountryCode <countryCode>
                -> byRating <from> <to>
                -> close
                """);
         while (true) {
             String command = scanner.nextLine();
             String[] commandParts = command.split("\\s+");

             if (commandParts.length > MAX_LENGTH_OF_FILTER_COMMAND)
                 throw new IncorrectFormatException("Unknown command");

             if (commandParts[0].equals("close"))
                 return filtersList;

             Predicate<Show> predicate = switch (commandParts[0]) {
                 case "byCountryCode" -> Show.countryCodeContains(commandParts[1]);
                 case "byRating" -> Show.ratingContains(Double.parseDouble(commandParts[1]), Double.parseDouble(commandParts[2]));
                 default -> throw new IncorrectFormatException("Unknown command");
             };

             filtersList.add(predicate);
         }
    }
}
