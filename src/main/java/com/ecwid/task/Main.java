package com.ecwid.task;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Incorrect number of arguments provided: " + args.length);
        }

        UniqueIpAddressCounter counter = new UniqueIpAddressCounter();
        counter.countUniqueIps(Path.of(args[0]));
    }
}
