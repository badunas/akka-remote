package com.badun.akkaremotedemo.util;

import akka.actor.*;
import akka.pattern.AskableActorSelection;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

/**
 * Created by Artsiom Badun.
 */
public class Selector {

    public static ActorRef select(String path, AbstractActorContext context) throws Exception {
        ActorSelection actorSelection = context.actorSelection(path);
        AskableActorSelection askableActorSelection = new AskableActorSelection(actorSelection);
        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        Future<Object> fut = askableActorSelection.ask(new Identify(1), timeout);
        ActorIdentity ident = (ActorIdentity) Await.result(fut, timeout.duration());
        ActorRef ref = ident.getRef();
        if (ref == null) {
            throw new RuntimeException("Can't select a remote worker.");
        }
        return ref;
    }
}
