package com.example.learning_scala

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {

  val migrationResult = Fixture
    .migrateDb(Fixture.dbConfig)
    .use(r => IO.delay(s"""
        |Migration result:
        |=======================
        |Success:                ${r.success}
        |Database:               ${r.database}
        |Schema name:            ${r.schemaName}
        |Initial schema version: ${r.initialSchemaVersion}
        |Target schema version:  ${r.targetSchemaVersion}
        |""".stripMargin))
  println("Migrating database")

  val run = for {
    _ <- migrationResult
    result <- Learning_scalaServer.run[IO]
  } yield result
}

// import cats.effect._
// import skunk._
// import skunk.implicits._
// import skunk.codec.all._
// import natchez.Trace.Implicits.noop                          // (1)

// object Hello extends IOApp {

//   val session: Resource[IO, Session[IO]] =
//     Session.single(                                          // (2)
//       host     = "localhost",
//       port     = 5432,
//       user     = "postgres",
//       database = "postgres",
//       password = Some("123456")
//     )

//   def run(args: List[String]): IO[ExitCode] =
//     session.use { s =>                                       // (3)
//       for {
//         d <- s.unique(sql"select current_date".query(date))  // (4)
//         _ <- IO.println(s"The current date is $d.")
//       } yield ExitCode.Success
//     }

// }
