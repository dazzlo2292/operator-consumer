package ru.app.core.dtos;

import lombok.Data;

@Data
public class Prefix {
    private Long id;
    private Long regionId;
    private String prefix;
    private boolean mnp;
    private boolean manualInsert;
}
