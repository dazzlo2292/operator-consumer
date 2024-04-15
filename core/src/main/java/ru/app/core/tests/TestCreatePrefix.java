package ru.app.core.tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.app.core.dtos.Prefix;
import ru.app.core.services.converters.PrefixConverter;

import java.io.IOException;

public class TestCreatePrefix {

    @Test
    public void CreatePrefix() throws IOException {

        PrefixConverter converter = new PrefixConverter();

        CSVParser csvParser = CSVParser.parse("1,3,79035225253,Y,Y", CSVFormat.EXCEL);
        Prefix prefix = csvParser.stream()
                .map(it -> converter.convert(it))
                .findFirst()
                .get();

        Assertions.assertEquals(1L,prefix.getId());
        Assertions.assertEquals(3L,prefix.getRegionId());
        Assertions.assertEquals("79035225253",prefix.getPrefix());
    }
}
