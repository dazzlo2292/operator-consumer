package ru.app.web.controllers.errors;

import lombok.Data;

@Data
public class ErrorView {
    private final String errorCode;
    private final String errorDetails;
}
