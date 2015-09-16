package com.badun.akkaremotedemo.worker;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.badun.akkaremotedemo.message.Msg;
import com.badun.akkaremotedemo.util.Sleeper;

/**
 * Created by Artsiom Badun.
 */
public class WorkerActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public WorkerActor() {
        receive(ReceiveBuilder
                .match(Msg.PieceOfWork.class, this::handleWorkMessage)
                .matchAny(this::unhandled)
                .build());
        log.debug("Created worker actor.");
    }

    private void handleWorkMessage(Msg.PieceOfWork message) {
        Sleeper.sleep(1);
        log.info("[WORKER] Actor handled message: " + message);
        sender().tell(new Msg.WorkDone(), context().parent());
        sender().tell(new Msg.WorkDone(), context().parent());
    }
}
