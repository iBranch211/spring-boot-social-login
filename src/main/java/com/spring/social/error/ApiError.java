package com.spring.social.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class ApiError {

    @NonNull
    private int status;

    @NonNull
    private String message;

    @NonNull
    private String path;

    @NonNull
    private long timestamp;

    private Map<String, String> errors;


}
