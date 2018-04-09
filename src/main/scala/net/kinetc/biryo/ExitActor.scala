package net.kinetc.biryo

import java.io.PrintWriter

import akka.actor.{Actor, ActorRef, Props}

object ExitActor {
  def props(): Props = Props(new ExitActor)

  case object Exit
}

class ExitActor extends Actor {

  import ExitActor._

  val shutdownCount = {
    val coreSize = Runtime.getRuntime.availableProcessors
    if (coreSize == 1) 1 else coreSize - 1
  } * 2

  var exitCount = 0
  def receive = {

    case Exit =>
      exitCount += 1
      if (exitCount == shutdownCount) {
        println("Shutdown Program!")
        context.system.terminate()
      }
  }
}