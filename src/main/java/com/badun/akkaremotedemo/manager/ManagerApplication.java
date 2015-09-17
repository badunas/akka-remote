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
        // 1. Create 'ManagerSystem' actor system.
        ActorSystem system = ActorSystem.create("ManagerSystem", ConfigFactory.load("manager"));
        // 2. Create Props object for ManagerActor actor.
        Props managerActorProps = Props.create(ManagerActor.class, "akka.tcp://WorkerSystem@127.0.0.1:2552/user/worker-router");
        // 3. Create Props object for 'manager-router' actor.
        Props managerRouterProps = FromConfig.getInstance().props(managerActorProps);
        // 4. Create 'manager-router' actor.
        system.actorOf(managerRouterProps, "manager-router");
    }
}
