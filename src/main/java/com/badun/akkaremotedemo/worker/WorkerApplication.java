package com.badun.akkaremotedemo.worker;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import com.badun.akkaremotedemo.manager.ManagerActor;
import com.typesafe.config.ConfigFactory;

/**
 * Created by Artsiom Badun.
 */
public class WorkerApplication {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("WorkerSystem", ConfigFactory.load("worker"));
        Props workerActorProps = Props.create(WorkerActor.class);
        Props workerRouterProps = FromConfig.getInstance().props(workerActorProps);
        system.actorOf(workerRouterProps, "worker-router");
    }
}
