package com.ms.codingchallenge.parking.service;

import com.ms.codingchallenge.parking.helper.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ParkingServiceTest {

    private static final Timer TIMER = new Timer();
    @Autowired
    private ParkingService parkingService;
    @Autowired
    private RandomGenerator randomGenerator;

    @Test
    public void testConcurrentParkUnpark() throws InterruptedException {

        var executor = Executors.newFixedThreadPool(10);
        // Park
        for (long i = 1; i <= 10; i++) {
            executor.execute(() -> parkingService.park(randomGenerator.randomRegistrationNumber()));
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
        assertEquals(0, parkingService.getAvailableParkingSpaces().size());
        assertEquals(10, parkingService.getOccupiedParkingSpaces().size());

        Thread.sleep(1000);

        // Unpark
        executor = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= 10; i++) {
            long longI = i;
            executor.execute(() -> parkingService.unpark(longI));
        }
        executor.shutdown();

        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS));
        assertEquals(10, parkingService.getAvailableParkingSpaces().size());
        assertEquals(0, parkingService.getOccupiedParkingSpaces().size());
    }
}

