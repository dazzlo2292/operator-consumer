package ru.app.core.tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.app.core.dtos.Country;
import ru.app.core.services.converters.CountryConverter;

import java.io.IOException;

public class TestCreateCountry {

    @Test
    public void CreateCountry() throws IOException {

        CountryConverter converter = new CountryConverter();

        CSVParser csvParser = CSVParser.parse("1,russia,Россия,Россия", CSVFormat.EXCEL);
        Country country = csvParser.stream()
                .map(it -> converter.convert(it))
                .findFirst()
                .get();

        Assertions.assertEquals(1L,country.getId());
        Assertions.assertEquals("russia",country.getCode());
        Assertions.assertEquals("Россия",country.getName());
        Assertions.assertEquals("Россия",country.getDescription());
    }
}
