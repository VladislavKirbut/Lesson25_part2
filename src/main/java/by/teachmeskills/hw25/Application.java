package by.teachmeskills.hw25;

import by.teachmeskills.hw25.config.RepositoryShowProperties;
import by.teachmeskills.hw25.models.Show;
import by.teachmeskills.hw25.repository.RepositoryShow;

import java.nio.file.Path;

public class Application {
    public static void main(String[] args) {
        RepositoryShowProperties properties = new RepositoryShowProperties(Path.of(System.getenv("PATH_TO_REPO")));
        RepositoryShow repository = new RepositoryShow(properties);

        for (Show show: repository.getAll()) {
            System.out.println(show);
        }
    }
}
