package com.ms.codingchallenge.parking;

import com.ms.codingchallenge.parking.exception.NoParkingSpaceAvailableException;
import com.ms.codingchallenge.parking.exception.NoSuchSpaceException;
import com.ms.codingchallenge.parking.model.Car;
import com.ms.codingchallenge.parking.model.CarPark;
import com.ms.codingchallenge.parking.model.ParkingSpace;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Repository
@Slf4j
public class ParkingRepository {

    @Value("${com.ms.codingchallenge.parking.carParkSize}")
    private int PARKING_SIZE;

    @Value("${com.ms.codingchallenge.parking.hourlyCharge}")
    private double HOURLY_CHARGE;

    private final List<ParkingSpace> availableParkingSpaces;
    private final Map<Long, CarPark> occupiedParkingSpaces;

    @Autowired
    public ParkingRepository(
            @Value("${com.ms.codingchallenge.parking.carParkSize}") int parkingSize,
            @Value("${com.ms.codingchallenge.parking.hourlyCharge}") long hourlyCharge
    ) {
        this.PARKING_SIZE = parkingSize;
        this.HOURLY_CHARGE = hourlyCharge;
        availableParkingSpaces = LongStream
                .range(1, PARKING_SIZE + 1)
                .boxed()
                .map(spaceNumber -> ParkingSpace.builder()
                        .spaceNumber(spaceNumber)
                        .isAvailable(true)
                        .build()
                ).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        occupiedParkingSpaces = new ConcurrentHashMap<>(PARKING_SIZE);
    }

    /**
     * Parks the car. Car should have valid registration number
     * @param car
     * @return the space number
     * @throws NoParkingSpaceAvailableException when no available space
     */
    public long park(@Valid Car car) throws NoParkingSpaceAvailableException {
        if (availableParkingSpaces.isEmpty()) {
            log.warn("No available space found to park the car [{}]", car);
            throw new NoParkingSpaceAvailableException("Sorry, parking is full!");
        }
        // Assuming that we are assigning the very first available parking space in the list
        ParkingSpace parkingSpace = availableParkingSpaces.remove(0);
        parkingSpace.setAvailable(false);
        CarPark carPark = CarPark
                .builder()
                .car(car)
                .parkingSpace(parkingSpace)
                .inTime(LocalDateTime.now())
                .build();
        log.debug("Parking the car [{}] in the parking space [{}]", car, parkingSpace);
        Long spaceNumber = parkingSpace.getSpaceNumber();
        occupiedParkingSpaces.put(spaceNumber, carPark);
        return spaceNumber;
    }

    /**
     * Unparks the charge and returns the parking charge.
     * @param spaceNumber
     * @return the charge
     */
    public double unpark(long spaceNumber) throws NoSuchSpaceException {
        log.debug("Request received to unpark the car at space# {}", spaceNumber);
        if (!occupiedParkingSpaces.containsKey(spaceNumber)) {
            log.warn("Trying to unpark the car from invalid space [{}]", spaceNumber);
            throw new NoSuchSpaceException("Invalid space number!");
        }
        CarPark carPark = occupiedParkingSpaces.get(spaceNumber);
        Duration duration = Duration.between(carPark.getInTime(), LocalDateTime.now());
        // Charge the customer for the next 1 hour if the car is parked after the completion of hour
        long parkedHours = duration.toHours() + (duration.toSeconds() >= 1 ? 1 : 0);
        double parkingCharge = (int) parkedHours * HOURLY_CHARGE;
        log.info("Car [{}] parked in the space [{}] for [{}] hours. Charge [{}]",
                carPark.getCar().getRegistrationNumber(),
                spaceNumber,
                parkedHours,
                parkingCharge
        );
        occupiedParkingSpaces.remove(spaceNumber);
        ParkingSpace parkingSpace = carPark.getParkingSpace();
        parkingSpace.setAvailable(true);
        availableParkingSpaces.add(parkingSpace);
        return parkingCharge;
    }

    public List<ParkingSpace> getAvailableParkingSpaces() {
        return Collections.unmodifiableList(availableParkingSpaces);
    }

    public Map<Long, CarPark> getOccupiedParkingSpaces() {
        return Collections.unmodifiableMap(occupiedParkingSpaces);
    }
}
