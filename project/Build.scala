import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization  := "com.softwaremill.scalamacrotutorial",
    version       := "0.1-SNAPSHOT",
    scalaVersion  := "2.10.3",
    licenses      := ("Apache2", new java.net.URL("http://www.apache.org/licenses/LICENSE-2.0.txt")) :: Nil,
    homepage      := Some(new java.net.URL("http://www.softwaremill.com")),
    resolvers     += "Expecty Repository" at "https://raw.github.com/pniederw/expecty/master/m2repo/"
  )
}

object ScalaMacroTutorialBuild extends Build {
  import BuildSettings._

  val expecty = "org.expecty" % "expecty" % "0.9"
  val macwire = "com.softwaremill.macwire" %% "core" % "0.4"
  val slick = "com.typesafe.slick" %% "slick" % "2.0.0"
  val h2 = "com.h2database" % "h2" % "1.3.175"
  val async = "org.scala-lang.modules" %% "scala-async" % "0.9.0-M4"
  val pickling = "org.scala-lang" %% "scala-pickling" % "0.8.0-SNAPSHOT"
  val akka = "com.typesafe.akka" %% "akka-actor" % "2.2.3"

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings
  ) aggregate(macros, usage, libraries)

  // A separate project for macros ...
  lazy val macros = Project(
    "macros",
    file("macros"),
    settings = buildSettings ++ Seq(
      // This dependency is needed to get access to the reflection API
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _))
  )

  // and for macros usage
  lazy val usage = Project(
    "usage",
    file("usage"),
    settings = buildSettings
  ) dependsOn(macros)

  lazy val libraries = Project(
    "libraries",
    file("libraries"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= Seq(expecty, macwire, slick, h2, async, pickling, akka),
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _), // for slick
      resolvers += Resolver.sonatypeRepo("snapshots"))
  )
}
