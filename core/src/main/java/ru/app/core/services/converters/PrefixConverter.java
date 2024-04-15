package ru.app.core.services.converters;

import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.app.core.dtos.Prefix;

import java.util.Objects;

@Component
public class PrefixConverter implements Converter<CSVRecord, Prefix> {
    @Override
    public Prefix convert(@NonNull CSVRecord record) {
        Prefix prefix = new Prefix();
        prefix.setId(Long.parseLong(record.get(0)));
        prefix.setRegionId(Long.parseLong(record.get(1)));
        prefix.setPrefix(record.get(2));
        prefix.setMnp(Objects.equals("Y", record.get(3)));
        prefix.setManualInsert(Objects.equals("Y", record.get(4)));

        return prefix;
    }
}
