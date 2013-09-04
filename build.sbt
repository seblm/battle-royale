import play.Project._

name := "battle"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
  )     

playScalaSettings
