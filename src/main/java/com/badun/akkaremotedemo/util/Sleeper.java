package com.badun.akkaremotedemo.util;

/**
 * Created by Artsiom Badun.
 */
public class Sleeper {

    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
