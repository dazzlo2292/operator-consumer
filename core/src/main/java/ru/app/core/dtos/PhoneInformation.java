package ru.app.core.dtos;

import lombok.Data;

@Data
public class PhoneInformation {
    private String countryCode;
    private String countryName;
    private String operatorCode;
    private String operatorName;
    private String regionCode;
    private String regionName;
    private String timezone;
}