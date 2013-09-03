import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization  := "com.softwaremill.scalamacrotutorial",
    version       := "0.1-SNAPSHOT",
    scalaVersion  := "2.10.2",
    licenses      := ("Apache2", new java.net.URL("http://www.apache.org/licenses/LICENSE-2.0.txt")) :: Nil,
    homepage      := Some(new java.net.URL("http://www.softwaremill.com")),
    resolvers     += "Expecty Repository" at "https://raw.github.com/pniederw/expecty/master/m2repo/"
  )
}

object ScalaMacroTutorialBuild extends Build {
  import BuildSettings._

  val expecty = "org.expecty" % "expecty" % "0.9"
  val macwire = "com.softwaremill.macwire" %% "core" % "0.4"

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
    settings = buildSettings ++ Seq(libraryDependencies ++= Seq(expecty, macwire))
  )
}
