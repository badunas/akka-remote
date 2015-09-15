package com.badun.akkaremotedemo.worker;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Created by Artsiom Badun.
 */
public class WorkerApplication {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("WorkerSystem", ConfigFactory.load("worker"));
        system.actorOf(Props.create(WorkerActor.class), "worker");
    }
}
