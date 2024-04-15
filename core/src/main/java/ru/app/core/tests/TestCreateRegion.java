package ru.app.core.tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.app.core.dtos.Region;
import ru.app.core.services.converters.RegionConverter;

import java.io.IOException;

public class TestCreateRegion {

    @Test
    public void CreateRegion() throws IOException {

        RegionConverter converter = new RegionConverter();

        CSVParser csvParser = CSVParser.parse("3,2,russia-mts-mts-moskva,Москва,Москва,Europe/Moscow", CSVFormat.EXCEL);
        Region region = csvParser.stream()
                .map(it -> converter.convert(it))
                .findFirst()
                .get();

        Assertions.assertEquals(3L,region.getId());
        Assertions.assertEquals(2L,region.getOperatorId());
        Assertions.assertEquals("russia-mts-mts-moskva",region.getCode());
        Assertions.assertEquals("Москва",region.getName());
        Assertions.assertEquals("Москва",region.getDescription());
        Assertions.assertEquals("Europe/Moscow",region.getTimeZone());
    }
}
