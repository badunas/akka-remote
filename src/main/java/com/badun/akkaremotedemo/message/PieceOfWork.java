package com.badun.akkaremotedemo.message;

/**
 * Created by Artsiom Badun.
 */
public class PieceOfWork extends BaseMessage {
    private final String message;

    public PieceOfWork(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
