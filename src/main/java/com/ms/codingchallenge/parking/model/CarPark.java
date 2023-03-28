package com.ms.codingchallenge.parking.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CarPark {
    @NotNull
    private Car car;
    @NotNull
    private ParkingSpace parkingSpace;
    private LocalDateTime inTime;
}
