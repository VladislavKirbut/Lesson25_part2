package by.teachmeskills.hw25.repository;

import by.teachmeskills.hw25.models.Series;
import by.teachmeskills.hw25.models.Show;

import java.time.Year;

public class SeriesDeserializer implements ShowDeserializer {
    @Override
    public Show deserialize(String line) {
        String[] linesPart = line.split(",");
        return new Series(linesPart[0],
                          Year.parse(linesPart[1]),
                          Year.parse(linesPart[2]),
                          linesPart[3],
                          Integer.parseInt(linesPart[4]),
                          Integer.parseInt(linesPart[5]),
                          Double.parseDouble(linesPart[6]),
                          Integer.parseInt(linesPart[7]));
    }
}
