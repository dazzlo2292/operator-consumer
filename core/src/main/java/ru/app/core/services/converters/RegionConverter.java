package ru.app.core.services.converters;

import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.app.core.dtos.Region;

@Component
public class RegionConverter implements Converter<CSVRecord, Region> {

    @Override
    public Region convert(@NonNull CSVRecord record) {
        Region region = new Region();
        region.setId(Long.parseLong(record.get(0)));
        region.setOperatorId(Long.parseLong(record.get(1)));
        region.setCode(record.get(2));
        region.setName(record.get(3));
        region.setDescription(record.get(4));
        region.setTimeZone(record.get(5));

        return region;
    }
}
