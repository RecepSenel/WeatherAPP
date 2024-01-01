package com.senel.weather.data;

public record ResponseData(
        Request request,
        Current current,
        location location
) {}
