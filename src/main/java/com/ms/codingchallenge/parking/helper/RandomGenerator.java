package com.ms.codingchallenge.parking.helper;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * A helper class to deal with random generation of registration numbers,
 * parking space etc.
 */
@Component
public class RandomGenerator {

    /**
     * Generates a random registration number and returns it.
     */
    public String randomRegistrationNumber() {
        return RandomStringUtils.randomAlphanumeric(7).toUpperCase();
    }

    /**
     * Randomly selects an occupied parking space and returns it.
     *
     * Returns -1 if the given set is empty
     */
    public long randomParkingSpace(Set<Long> occupiedParkingSpaces) throws NoSuchAlgorithmException {
        // Select a random space & return it. Return -1 if there are none occupied.
        return occupiedParkingSpaces.size() <= 0 ? -1 : new ArrayList<>(occupiedParkingSpaces)
                .get(SecureRandom.getInstanceStrong().nextInt(occupiedParkingSpaces.size()));
    }

    /**
     * Randomly selects an occupied parking space and returns it.
     *
     * Returns -1 if the given set is empty
     */
    public int randomInt(int max) throws NoSuchAlgorithmException {
        // Select a random space & return it. Return -1 if there are none occupied.
        return SecureRandom.getInstanceStrong().nextInt(max);
    }
}
