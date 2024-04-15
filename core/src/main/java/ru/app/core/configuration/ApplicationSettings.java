package ru.app.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("application")
public class ApplicationSettings {
    private String filesDirectory = "./files";
    private String countryFilename = "countries.csv";
    private String operatorFilename = "operators.csv";
    private String regionFilename = "regions.csv";
    private String prefixFilename = "prefixes.csv";
}
