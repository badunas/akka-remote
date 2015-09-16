package com.badun.akkaremotedemo.message;

import java.io.Serializable;

/**
 * Created by Artsiom Badun.
 */
public interface Msg {

    abstract class BaseMessage implements Serializable {
    }

    class PieceOfWork extends BaseMessage {
        private final String message;

        public PieceOfWork(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    class WorkDone extends BaseMessage {
    }
}
