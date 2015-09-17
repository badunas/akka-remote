package com.badun.akkaremotedemo.manager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ReceiveTimeout;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.badun.akkaremotedemo.message.Msg;
import com.badun.akkaremotedemo.util.Selector;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Artsiom Badun.
 */
public class ManagerActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final String initialWorkerPath;
    private int taskNumber = 1000;

    public ManagerActor(String initialWorkerPath) {
        // Message class - handler mapping.
        receive(ReceiveBuilder
                .match(ReceiveTimeout.class, this::handleTimeoutMessage)
                .match(Msg.WorkDone.class, this::handleWorkerMessage)
                .matchAny(this::unhandled)
                .build());
        this.initialWorkerPath = initialWorkerPath;
        log.debug("Created manager actor with initialWorkerPath: " + initialWorkerPath);
    }

    @Override
    public void preStart() {
        // Schedule to send ReceiveTimeout messages to itself.
        context().setReceiveTimeout(Duration.create(5, TimeUnit.SECONDS));
    }

    private void handleTimeoutMessage(ReceiveTimeout timeout) {
        try {
            // Select an available actor with the specified actor path.
            ActorRef worker = Selector.select(initialWorkerPath, getContext());
            worker.tell(buildWorkMessage(), self());
            log.info("[MANAGER] Manager send a peace of work to worker after timeout.");
        } catch (Exception e) {
            log.error("Worker not found. " + e.getMessage());
        }
    }

    private void handleWorkerMessage(Msg.WorkDone message) {
        sender().tell(buildWorkMessage(), self());
        log.info("[MANAGER] Manager send a peace of work to worker by worker request.");
    }

    private Msg.PieceOfWork buildWorkMessage() {
        return new Msg.PieceOfWork("Task: " + self().path() + "_" + taskNumber++);
    }
}
