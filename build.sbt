//val orgName = "io.github.daviddenton"
//
//val projectName = "fintrospect"
//
//organization := orgName
//
//name := projectName
//
//description := "Library that adds self-documentation to Finagle server endpoint services"
//
//pomExtra :=
//  <url>http://fintrospect.io</url>
//    <scm>
//      <url>git@github.com:daviddenton/fintrospect.git</url>
//      <connection>scm:git:git@github.com:daviddenton/fintrospect.git</connection>
//      <developerConnection>scm:git:git@github.com:daviddenton/fintrospect.git</developerConnection>
//    </scm>
//    <developers>
//      <developer>
//        <name>David Denton</name>
//        <email>dev@fintrospect.io</email>
//        <organization>fintrospect</organization>
//        <organizationUrl>http://fintrospect.io</organizationUrl>
//      </developer>
//    </developers>


//Seq(bintraySettings: _*)

lazy val baseSettings = Seq(
//  licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  scalacOptions := Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    //    "-language:existentials",
    //    "-language:higherKinds",
    //    "-language:implicitConversions",
    //    "-unchecked",
    //    "-Yno-adapted-args",
    //    "-Ywarn-dead-code",
    //    "-Ywarn-numeric-widen",
    //    "-Xfuture",
    //    "-Xlint"
    "-feature"
  ),
  libraryDependencies := Seq(
    "net.sourceforge.argo" % "argo" % "3.12",
    "com.gilt" %% "handlebars-scala" % "2.0.1" % "provided",
    "com.github.spullara.mustache.java" % "compiler" % "0.9.1" % "provided",
    "com.github.spullara.mustache.java" % "scala-extensions-2.11" % "0.9.1" % "provided",
    "com.google.code.gson" % "gson" % "2.5" % "provided",
    "io.circe" %% "circe-core" % "0.4.1" % "provided",
    "io.circe" %% "circe-generic" % "0.4.1" % "provided",
    "io.circe" %% "circe-parser" % "0.4.1" % "provided",
    "io.spray" %% "spray-json" % "1.3.2" % "provided",
    "io.argonaut" %% "argonaut" % "6.0.4" % "provided",
    "com.typesafe.play" %% "play-json" % "2.4.3" % "provided",
    "org.json4s" %% "json4s-native" % "3.3.0" % "provided",
    "org.json4s" %% "json4s-jackson" % "3.3.0" % "provided",
    "com.twitter" %% "finagle-http" % "6.35.0" % "provided",
    "com.github.finagle" %% "finagle-oauth2" % "0.1.6" % "provided",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  )

)

lazy val buildSettings = Seq(
  organization := "io.fintrospect",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.6", "2.11.8")
)

lazy val allSettings = baseSettings ++ buildSettings

lazy val core = project
  .settings(moduleName := "fintrospect-core")
  .settings(allSettings)

lazy val fintrospect = project.in(file("."))
  .settings(moduleName := "fintrospect")
  .settings(allSettings)
  .aggregate(core)
  .dependsOn(core)