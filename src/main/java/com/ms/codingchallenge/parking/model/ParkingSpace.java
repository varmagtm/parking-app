package com.ms.codingchallenge.parking.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class ParkingSpace {
    @NotNull
    private Long spaceNumber;
    @EqualsAndHashCode.Exclude
    private boolean isAvailable;
}
