package ru.app.core.tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.app.core.dtos.Operator;
import ru.app.core.services.converters.OperatorConverter;

import java.io.IOException;

public class TestCreateOperator {

    @Test
    public void CreateOperator() throws IOException {

        OperatorConverter converter = new OperatorConverter();

        CSVParser csvParser = CSVParser.parse("2,1,russia-mts,МТС,МТС", CSVFormat.EXCEL);
        Operator operator = csvParser.stream()
                .map(it -> converter.convert(it))
                .findFirst()
                .get();

        Assertions.assertEquals(2L,operator.getId());
        Assertions.assertEquals(1L,operator.getCountryId());
        Assertions.assertEquals("russia-mts",operator.getCode());
        Assertions.assertEquals("МТС",operator.getName());
        Assertions.assertEquals("МТС",operator.getDescription());
    }
}
