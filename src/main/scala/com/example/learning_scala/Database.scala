package com.example.learning_scala

import fly4s.core._
import fly4s.core.data._
import cats.effect._
import fly4s.implicits._

case class DatabaseConfig(
    url: String,
    user: Option[String],
    password: Option[Array[Char]],
    migrationsTable: String,
    migrationsLocations: List[String]
)

object Fixture {
  def migrateDb(dbConfig: DatabaseConfig): Resource[IO, MigrateResult] =
    Fly4s
      .make[IO](
        url = dbConfig.url,
        user = dbConfig.user,
        password = dbConfig.password,
        config = Fly4sConfig(
          table = dbConfig.migrationsTable,
          locations = Locations(dbConfig.migrationsLocations),
          ignoreMigrationPatterns = List(
            ValidatePattern.ignorePendingMigrations
          )
        )
      )
      .evalMap(_.validateAndMigrate.result)

  val dbConfig: DatabaseConfig = DatabaseConfig(
    // url = "jdbc:postgresql://localhost:5432/postgres",
    url = "jdbc:postgresql://localhost:5432/postgres",
    user = Some("postgres"),
    password = Some("123456".toArray[Char]),
    migrationsTable = "flyway",
    migrationsLocations = List("db")
  )

}
