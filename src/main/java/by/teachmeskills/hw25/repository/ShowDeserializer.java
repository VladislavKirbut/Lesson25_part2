package by.teachmeskills.hw25.repository;

import by.teachmeskills.hw25.models.Show;

public interface ShowDeserializer {
    Show deserialize(String line);
}
