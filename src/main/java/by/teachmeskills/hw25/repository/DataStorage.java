package by.teachmeskills.hw25.repository;

import by.teachmeskills.hw25.models.Show;

import java.nio.file.Path;
import java.util.List;

public interface DataStorage {

    List<Show> getAll();
}
