package com.badun.akkaremotedemo.manager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ReceiveTimeout;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.badun.akkaremotedemo.message.PieceOfWork;
import com.badun.akkaremotedemo.message.WorkDone;
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
        receive(ReceiveBuilder
                .match(ReceiveTimeout.class, this::handleTimeoutMessage)
                .match(WorkDone.class, this::handleWorkerMessage)
                .matchAny(this::unhandled)
                .build());
        this.initialWorkerPath = initialWorkerPath;
        context().setReceiveTimeout(Duration.create(5, TimeUnit.SECONDS));
        log.debug("Created manager actor with initialWorkerPath: " + initialWorkerPath);
    }

    private void handleTimeoutMessage(ReceiveTimeout timeout) {
        try {
            ActorRef worker = Selector.select(initialWorkerPath, getContext());
            worker.tell(buildWorkMessage(), self());
            log.info("[MANAGER] Manager send a peace of work to worker after timeout.");
        } catch (Exception e) {
            log.error("Worker not found. " + e.getMessage());
        }
    }

    private void handleWorkerMessage(WorkDone message) {
        sender().tell(buildWorkMessage(), self());
        log.info("[MANAGER] Manager send a peace of work to worker by worker request.");
    }

    private PieceOfWork buildWorkMessage() {
        return new PieceOfWork("Task: " + self().path() + "_" + taskNumber++);
    }
}
