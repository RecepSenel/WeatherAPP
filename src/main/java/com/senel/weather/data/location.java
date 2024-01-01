package com.senel.weather.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record location(
        String name,
        String country,
        String localtime,
        String region,
        String lat,
        String lon,
        String timezone_id,
        String localtime_epoch,
        @JsonProperty("utc_offset")
        String utcOffset
) {}
