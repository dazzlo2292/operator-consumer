package ru.app.core.dtos;

import lombok.Data;

@Data
public class Region {
    private Long id;
    private Long operatorId;
    private String code;
    private String name;
    private String description;
    private String timeZone;
}
