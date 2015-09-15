package com.badun.akkaremotedemo.manager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.badun.akkaremotedemo.message.PieceOfWork;
import com.badun.akkaremotedemo.worker.WorkerActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Date;

/**
 * Created by Artsiom Badun.
 */
public class ManagerApplication {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("ManagerSystem", ConfigFactory.load("manager"));
        system.actorOf(
                Props.create(ManagerActor.class, "akka.tcp://WorkerSystem@127.0.0.1:2552/user/worker"),
                "manager");
    }
}
