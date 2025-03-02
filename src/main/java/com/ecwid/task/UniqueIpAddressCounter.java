package com.ecwid.task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniqueIpAddressCounter {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueIpAddressCounter.class);

    public long countUniqueIps(Path path) {
        BitSet positiveBitSet = new BitSet(Integer.MAX_VALUE);
        BitSet negativeBitSet = new BitSet(Integer.MAX_VALUE);
        AtomicLong counter = new AtomicLong();

        try (var ipAddressStream = Files.lines(path, StandardCharsets.US_ASCII)) {
            ipAddressStream
                    .mapToInt(this::convertIpV4ToInt)
                    .forEach(value -> {
                        counter.getAndIncrement();
                        if (value >= 0) {
                            positiveBitSet.set(value);
                        } else {
                            negativeBitSet.set(~value);
                        }
                    });

            long uniqueIpsCount = positiveBitSet.cardinality() + negativeBitSet.cardinality();
            LOGGER.info("IPs processed: {}", counter);
            LOGGER.info("Unique IPs found: {}", uniqueIpsCount);
            return uniqueIpsCount;
        } catch (IOException e) {
            LOGGER.error("Error while reading file {}", path);
            throw new RuntimeException("Error while reading file", e);
        }
    }

    private int convertIpV4ToInt(String ip) {
        long result = 0;
        long octetValue = 0;

        for (int i = 0; i < ip.length(); i++) {
            char currentChar = ip.charAt(i);

            if (Character.isDigit(currentChar)) {
                int currentNumber = currentChar - '0';          //gets number from difference between ASCII characters
                octetValue = octetValue * 10 + currentNumber;
            } else if (currentChar == '.') {
                result = (result << Byte.SIZE) | octetValue;    //adds value of current octet to result
                octetValue = 0;
            }
        }

        return (int) ((result << Byte.SIZE) | octetValue);
    }
}