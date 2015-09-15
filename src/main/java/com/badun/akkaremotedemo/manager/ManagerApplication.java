package com.badun.akkaremotedemo.manager;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import com.typesafe.config.ConfigFactory;

/**
 * Created by Artsiom Badun.
 */
public class ManagerApplication {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("ManagerSystem", ConfigFactory.load("manager"));
        Props managerActorProps = Props.create(ManagerActor.class, "akka.tcp://WorkerSystem@127.0.0.1:2552/user/worker-router");
        Props managerRouterProps = FromConfig.getInstance().props(managerActorProps);
        system.actorOf(managerRouterProps, "manager-router");
    }
}
