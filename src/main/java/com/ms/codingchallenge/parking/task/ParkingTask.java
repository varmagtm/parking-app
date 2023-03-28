package com.ms.codingchallenge.parking.task;

import com.ms.codingchallenge.parking.helper.RandomGenerator;
import com.ms.codingchallenge.parking.service.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * A timer task to facilitate the car parking scenario.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ParkingTask extends TimerTask {

    private final ParkingService parkingService;

    private final RandomGenerator randomGenerator;

    @Override
    public void run() {
        String registrationNumber = randomGenerator.randomRegistrationNumber();
        log.debug("Car [{}] entered the parking lot, looking for parking", registrationNumber);
        this.parkingService.park(registrationNumber);
    }
}
