package com.ms.codingchallenge.parking.timer;

import com.ms.codingchallenge.parking.helper.RandomGenerator;
import com.ms.codingchallenge.parking.service.ParkingService;
import com.ms.codingchallenge.parking.task.ParkingTask;
import com.ms.codingchallenge.parking.task.UnparkingTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.stream.IntStream;

/**
 * Component for randomly scheduling car park/unpark events.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ParkingScheduler {

//    private static final Timer TIMER = new Timer();
//
//    private final ParkingService parkingService;
//
//    private final RandomGenerator randomGenerator;
//
//    /**
//     * Start scheduling the park or unpark events on the application startup.
//     *
//     * Do this indefinitely with a delay for simulation of the events.
//     * @throws InterruptedException
//     */
//    // @EventListener(ApplicationReadyEvent.class)
//    public void startup() throws InterruptedException {
//        while (true) {
//            // Schedule parking & unparking events
//            this.scheduleRandomEvents();
//            // Wait for a while
//            Thread.sleep(10000);
//        }
//    }
//
//    public void scheduleRandomEvents() {
//        try {
//            // Schedule a maximum of 14 parking events
//            this.scheduleRandomParkingEvents(
//                    randomGenerator.randomInt(15),
//                    randomGenerator.randomInt(5000),
//                    true
//            );
//            // Schedule a maximum of 9 unparking events
//            this.scheduleRandomParkingEvents(
//                    randomGenerator.randomInt(10),
//                    randomGenerator.randomInt(5000),
//                    false
//            );
//        } catch (NoSuchAlgorithmException e) {
//            log.error("Error in scheduling events", e);
//            System.exit(1);
//        }
//    }
//
//    /**
//     * Just for the sake of demonstration, generate random number
//     * of parking events at random intervals.
//     */
//    private void scheduleRandomParkingEvents(int eventsCount, int delay, boolean isParking) {
//        IntStream.range(1, eventsCount)
//                .boxed()
//                .forEach(i -> TIMER.schedule(
//                        isParking ? new ParkingTask(parkingService, randomGenerator)
//                                : new UnparkingTask(parkingService, randomGenerator),
//                        delay
//                ));
//    }
}
