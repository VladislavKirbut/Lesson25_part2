package by.teachmeskills.hw25;

import by.teachmeskills.hw25.config.RepositoryShowProperties;
import by.teachmeskills.hw25.controller.ShowCompileController;
import by.teachmeskills.hw25.controller.ShowController;
import by.teachmeskills.hw25.repository.RepositoryShow;
import by.teachmeskills.hw25.service.ShowCompileService;
import by.teachmeskills.hw25.service.ShowService;

import java.nio.file.Path;

public class Application {
    public static void main(String[] args) {
        RepositoryShowProperties properties = new RepositoryShowProperties(Path.of(System.getenv("PATH_TO_REPO")));
        RepositoryShow repository = new RepositoryShow(properties);
        ShowService service = new ShowCompileService(repository);
        ShowController controller = new ShowCompileController(service);

        controller.run();
    }
}
