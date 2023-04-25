package by.teachmeskills.hw25.repository;

import by.teachmeskills.hw25.models.Movie;
import by.teachmeskills.hw25.models.Show;

import java.time.Year;

public class MovieDeserializer implements ShowDeserializer {
    @Override
    public Show deserialize(String line) {
        String[] lineParts = line.split(",");
        return new Movie(lineParts[0],
                         Year.of(Integer.parseInt(lineParts[1])),
                         lineParts[2],
                         Double.parseDouble(lineParts[3]),
                         Integer.parseInt(lineParts[4])
        );
    }
}
