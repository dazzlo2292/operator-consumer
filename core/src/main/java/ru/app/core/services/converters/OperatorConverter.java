package ru.app.core.services.converters;

import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.app.core.dtos.Operator;

@Component
public class OperatorConverter implements Converter<CSVRecord, Operator> {

    @Override
    public Operator convert(CSVRecord record) {
        Operator operator = new Operator();
        operator.setId(Long.parseLong(record.get(0)));
        operator.setCountryId(Long.parseLong(record.get(1)));
        operator.setCode(record.get(2));
        operator.setName(record.get(3));
        operator.setDescription(record.get(4));

        return operator;
    }

}
