package com.pinapp.challenge.domain.model;

public record LifeExpectancy(
    Integer year,
    Double expectancyFemale,
    Double expectancyMale,
    Double expectancyOverall
) {

}
