package by.teachmeskills.hw25.repository;

import by.teachmeskills.hw25.config.RepositoryShowProperties;
import by.teachmeskills.hw25.models.Show;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RepositoryFileShow implements RepositoryShow {
    private final static Path FILMS = Path.of("films.csv");
    private final static Path SERIES = Path.of("series.csv");

    private final RepositoryShowProperties properties;

    public RepositoryFileShow(RepositoryShowProperties properties) {
        Objects.requireNonNull(properties);
        this.properties = properties;
    }
    @Override
    public List<Show> getAll() {
        List<Show> listShow = new ArrayList<>();
        getShowList(listShow, FILMS, new MovieDeserializer());
        getShowList(listShow, SERIES, new SeriesDeserializer());
        return listShow;
    }

    private void getShowList(List<Show> showList, Path path, ShowDeserializer deserializer) {
        Path pathToFile = properties.getPathToFile().resolve(path);

        try (Stream<String> csvLines = Files.lines(pathToFile, StandardCharsets.UTF_8)){
            showList.addAll(csvLines.map(deserializer::deserialize).toList());
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
