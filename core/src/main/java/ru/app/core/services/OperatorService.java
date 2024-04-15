package ru.app.core.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.app.core.configuration.ApplicationSettings;
import ru.app.core.dtos.MnpInformation;
import ru.app.core.dtos.PhoneInformation;
import ru.app.core.dtos.Prefix;
import ru.app.core.dtos.Region;
import ru.app.core.repositories.*;
import ru.app.core.services.converters.CountryConverter;
import ru.app.core.services.converters.OperatorConverter;
import ru.app.core.services.converters.PrefixConverter;
import ru.app.core.services.converters.RegionConverter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final Logger logger = LoggerFactory.getLogger(OperatorService.class);

    private final ApplicationSettings applicationSettings;

    // repositories

    private final PhoneInformationRepository phoneInformationRepository;

    private final CountryRepository countryRepository;
    private final OperatorRepository operatorRepository;
    private final RegionRepository regionRepository;
    private final PrefixRepository prefixRepository;

    // converters

    private final CountryConverter countryConverter;
    private final OperatorConverter operatorConverter;
    private final RegionConverter regionConverter;
    private final PrefixConverter prefixConverter;

    @Transactional
    public void syncOperators() {

        try {

            logger.info("Sync operators starting");

            // delete all with cascade

            countryRepository.deleteAll();

            // countries

            CSVFormat countryFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("id", "name", "code", "description")
                    .setSkipHeaderRecord(true)
                    .build();

            String countriesFilename = applicationSettings.getFilesDirectory() + "/" + applicationSettings.getCountryFilename();
            saveEntities(countryFormat, countriesFilename, countryConverter, countryRepository);

            // operators

            CSVFormat operatorFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("id", "countryId", "name", "code", "description")
                    .setSkipHeaderRecord(true)
                    .build();

            String operatorsFilename = applicationSettings.getFilesDirectory() + "/" + applicationSettings.getOperatorFilename();
            saveEntities(operatorFormat, operatorsFilename, operatorConverter, operatorRepository);

            // regions

            CSVFormat regionFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("id", "operatorId", "name", "code", "description", "timeZone")
                    .setSkipHeaderRecord(true)
                    .build();

            String regionsFilename = applicationSettings.getFilesDirectory() + "/" + applicationSettings.getRegionFilename();
            saveEntities(regionFormat, regionsFilename, regionConverter, regionRepository);

            // prefixes

            CSVFormat prefixFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("id", "regionId", "name", "code", "description")
                    .setSkipHeaderRecord(true)
                    .build();

            String prefixesFilename = applicationSettings.getFilesDirectory() + "/" + applicationSettings.getPrefixFilename();
            saveEntities(prefixFormat, prefixesFilename, prefixConverter, prefixRepository);

            logger.info("Sync operators completed");

        } catch (Exception e) {
            logger.error("Sync error: {}", e.getMessage());
            throw new IllegalArgumentException("sync error: " + e.getMessage());
        }
    }

    private <T> void saveEntities(CSVFormat format, String filename, Converter<CSVRecord, T> converter, EntityRepository<T> repository)
            throws IOException
    {
        Reader reader = new FileReader(filename);

        CSVParser parser = format.parse(reader);

        Iterator<CSVRecord> iterator = parser.iterator();

        while (iterator.hasNext()) {
            List<T> records = readBatch(iterator, converter, 100);
            repository.insert(records);
        }
    }

    private <T> List<T> readBatch(Iterator<CSVRecord> iterator, Converter<CSVRecord, T> converter, int batchSize) {
        int position = 0;
        List<T> records = new ArrayList<>();

        while (iterator.hasNext() && position < batchSize) {
            CSVRecord record = iterator.next();
            records.add(converter.convert(record));
            position++;
        }

        return records;
    }

    public PhoneInformation getPhoneInformation(String phone) {
        String currentPrefix = phone;

        while(currentPrefix.length() > 0) {
            PhoneInformation phoneInformation = phoneInformationRepository.getPhoneInformation(currentPrefix);

            if (phoneInformation == null) {
                currentPrefix = currentPrefix.substring(0, currentPrefix.length() - 1);
                continue;
            }
            return phoneInformation;
        }
        throw new IllegalArgumentException("Information by phone [" + phone + "] not found");
    }

    @Transactional
    public void addMnpPrefix(MnpInformation mnpInformation) {
        Region region = regionRepository.getByCode(mnpInformation.getRegionCode());

        if (region == null) {
            throw new IllegalArgumentException("Region with code [" + mnpInformation.getRegionCode() + "] not found");
        }

        Prefix prefix = new Prefix();
        prefix.setId(mnpInformation.getId());
        prefix.setRegionId(region.getId());
        prefix.setPrefix(mnpInformation.getPrefix());
        prefix.setMnp(mnpInformation.isMnp());
        prefix.setManualInsert(true);

        prefixRepository.insert(Collections.singletonList(prefix));
    }

}
