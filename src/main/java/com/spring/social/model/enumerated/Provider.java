package com.spring.social.model.enumerated;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Provider {

    PROVIDER_GOOGLE("GOOGLE"),
    PROVIDER_AZURE("AZURE"),
    PROVIDER_GITHUB("GITHUB"),
    PROVIDER_LOCAL("LOCAL");

    @NonNull String value;
}
