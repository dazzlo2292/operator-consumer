package ru.app.core.dtos;

import lombok.Data;

@Data
public class Country {
    private Long id;
    private String code;
    private String name;
    private String description;
}
