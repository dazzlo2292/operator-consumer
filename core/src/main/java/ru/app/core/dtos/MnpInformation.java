package ru.app.core.dtos;

import lombok.Data;

@Data
public class MnpInformation {
    private Long id;
    private String prefix;
    private boolean mnp;
    private String regionCode;
}
