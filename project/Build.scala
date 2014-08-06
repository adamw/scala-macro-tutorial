import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization  := "com.softwaremill.scalamacrotutorial",
    version       := "0.1-SNAPSHOT",
    scalaVersion  := "2.11.1",
    licenses      := ("Apache2", new java.net.URL("http://www.apache.org/licenses/LICENSE-2.0.txt")) :: Nil,
    homepage      := Some(new java.net.URL("http://www.softwaremill.com")),
    resolvers     += "Expecty Repository" at "https://raw.github.com/pniederw/expecty/master/m2repo/"
  )
}

object ScalaMacroTutorialBuild extends Build {
  import BuildSettings._

  val macwire = "com.softwaremill.macwire" %% "macros" % "0.7"
  val async = "org.scala-lang.modules" %% "scala-async" % "0.9.2"
  val pickling = "org.scala-lang" %% "scala-pickling" % "0.9.0-SNAPSHOT"
  val blitz = "com.github.scala-blitz" %% "scala-blitz" % "1.1"

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
      libraryDependencies ++= Seq(pickling, macwire, async, blitz),
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _), // for slick
      resolvers += Resolver.sonatypeRepo("snapshots"))
  )
}
