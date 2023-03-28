package com.ms.codingchallenge.parking.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Car {
    @NotBlank
    private String registrationNumber;
}
