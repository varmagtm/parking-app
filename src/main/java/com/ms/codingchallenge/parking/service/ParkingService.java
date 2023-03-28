package com.ms.codingchallenge.parking.service;

import com.ms.codingchallenge.parking.ParkingRepository;
import com.ms.codingchallenge.parking.exception.NoParkingSpaceAvailableException;
import com.ms.codingchallenge.parking.exception.NoSuchSpaceException;
import com.ms.codingchallenge.parking.model.Car;
import com.ms.codingchallenge.parking.model.CarPark;
import com.ms.codingchallenge.parking.model.ParkingSpace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public long park(String registrationNumber) {
        try {
            long parkingSpace = parkingRepository.park(
                    Car.builder()
                            .registrationNumber(registrationNumber)
                            .build()
            );
            log.info("Car [{}] parked in parking space# {}", registrationNumber, parkingSpace);
            return parkingSpace;
        } catch (NoParkingSpaceAvailableException e) {
            log.warn("No parking space available, trying after sometime");
            try {
                Thread.sleep(5000);
                this.park(registrationNumber);
            } catch (InterruptedException ex) {
                log.error("Error while await", ex);
            }
        }
        return 0;
    }

    public double unpark(long spaceNumber) {
        try {
            double space = this.parkingRepository.unpark(spaceNumber);
            log.info("Car removed from the parking space# {}", spaceNumber);
            return space;
        } catch (NoSuchSpaceException e) {
            log.error("Invalid space");
        }
        return 0;
    }

    public Map<Long, CarPark> getOccupiedParkingSpaces() {
        return this.parkingRepository.getOccupiedParkingSpaces();
    }

    public List<ParkingSpace> getAvailableParkingSpaces() {
        return this.parkingRepository.getAvailableParkingSpaces();
    }
}
