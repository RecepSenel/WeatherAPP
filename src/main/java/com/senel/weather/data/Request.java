package com.senel.weather.data;

public record Request(
        String type,
        String query,
        String language,
        String unit
) {
}
