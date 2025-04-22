package com.javarush.pavlenko;

import com.javarush.pavlenko.util.SpeedTest;

public class Main {

    public static void main(String[] args) {
        SpeedTest speedTest = new SpeedTest();
        speedTest.getAllData();
        speedTest.run();
        speedTest.shutdown();
    }
}