package com.ms.codingchallenge.parking.task;

import com.ms.codingchallenge.parking.helper.RandomGenerator;
import com.ms.codingchallenge.parking.service.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * A timer task to facilitate the car unparking scenario.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UnparkingTask extends TimerTask {

    private final ParkingService parkingService;

    private final RandomGenerator randomGenerator;

    @SneakyThrows
    @Override
    public void run() {
        var parkingSpace = this.randomGenerator.randomParkingSpace(
                this.parkingService.getOccupiedParkingSpaces().keySet());
        log.debug("Unparking the car from parking space [{}]", parkingSpace);
        if (parkingSpace != -1) {
            this.parkingService.unpark(parkingSpace);
        }
    }
}
