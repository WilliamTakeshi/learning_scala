package com.example.learning_scala

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  val run = Learning_scalaServer.run[IO]
}
