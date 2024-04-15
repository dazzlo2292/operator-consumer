package ru.app.core.dtos;

import lombok.Data;

@Data
public class Operator {
    private Long id;
    private Long countryId;
    private String code;
    private String name;
    private String description;
}
