package by.teachmeskills.hw25.service;

import by.teachmeskills.hw25.models.Show;
import by.teachmeskills.hw25.repository.RepositoryShow;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ShowCompileService implements ShowService {
    private final RepositoryShow repositoryShow;

    public ShowCompileService(RepositoryShow repositoryShow) {
        Objects.requireNonNull(repositoryShow);

        this.repositoryShow = repositoryShow;
    }

    @Override
    public List<Show> getShowList(Comparator<Show> sorting, Predicate<Show> filters) {
        List<Show> showList = repositoryShow.getAll();
        if (sorting != null)
            showList.sort(sorting);
        if (filters != null)
            showList.removeIf(filters.negate());

        return showList;
    }
}
