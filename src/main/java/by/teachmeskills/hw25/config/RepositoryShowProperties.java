package by.teachmeskills.hw25.config;

import java.nio.file.Path;
import java.util.Objects;

public class RepositoryShowProperties {
    private final Path pathToFile;

    public RepositoryShowProperties(Path pathToFile) {
        Objects.requireNonNull(pathToFile);
        this.pathToFile = pathToFile;
    }

    public Path getPathToFile() {
        return pathToFile;
    }
}
