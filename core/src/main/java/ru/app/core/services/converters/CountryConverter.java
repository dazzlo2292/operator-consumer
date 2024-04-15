package ru.app.core.services.converters;

import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.app.core.dtos.Country;

@Component
public class CountryConverter implements Converter<CSVRecord, Country> {

    @Override
    public Country convert(@NonNull CSVRecord record) {
        Country country = new Country();
        country.setId(Long.parseLong(record.get(0)));
        country.setCode(record.get(1));
        country.setName(record.get(2));
        country.setDescription(record.get(3));

        return country;
    }
}
