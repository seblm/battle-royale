import play.Project._

name := "battle"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.jsoup" % "jsoup" % "1.7.2"
  )     

playScalaSettings
