import Dependencies._

lazy val baseSettings = Seq(
  name := "fintrospect",
  organization := "io.fintrospect",
  version := "15.1.0",
  scalaVersion := "2.12.8",
  crossScalaVersions := Seq("2.12.8", "2.11.12"),
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  resolvers += "JCenter" at "https://jcenter.bintray.com",
  scalacOptions := Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-feature"
  ),
  pomExtra :=
    <url>http://fintrospect.io</url>
      <scm>
        <url>git@github.com:daviddenton/fintrospect.git</url>
        <connection>scm:git:git@github.com:daviddenton/fintrospect.git</connection>
        <developerConnection>scm:git:git@github.com:daviddenton/fintrospect.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <name>David Denton</name>
          <email>dev@fintrospect.io</email>
          <organization>fintrospect</organization>
          <organizationUrl>http://fintrospect.io</organizationUrl>
        </developer>
      </developers>,
  bintrayOrganization := Some("fintrospect"),
  credentials += Credentials(Path.userHome / ".sonatype" / ".credentials")
)

lazy val core = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-core")
  .settings(libraryDependencies ++= Seq(
    "org.apache.commons" % "commons-lang3" % commonsLang3Version,
    "net.sourceforge.argo" % "argo" % argoVersion,
    "com.twitter" %% "finagle-http" % finagleVersion,
    "com.google.guava" % "guava" % guavaVersion,
    "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion,
    "com.sun.activation" % "javax.activation" % javaXActivationVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  ))
  .settings(description := "Implement fast, type-safe HTTP contracts for Finagle (aka Twitter RPC)")

lazy val argonaut = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-argonaut")
  .settings(description := "Argonaut JSON library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies += "io.argonaut" %% "argonaut" % argonautVersion)

lazy val circe = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-circe")
  .settings(description := "Circe JSON library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies ++= Seq("io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion)
  )

lazy val gson = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-gson")
  .settings(description := "GSON JSON library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies += "com.google.code.gson" % "gson" % gsonVersion)

lazy val jackson = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-jackson")
  .settings(description := "Jackson JSON library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion)

lazy val json4s = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-json4s")
  .settings(description := "Json4S JSON library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies ++= Seq("org.json4s" %% "json4s-native" % json4sVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion))

lazy val play = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-play")
  .settings(description := "Play JSON library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies += "com.typesafe.play" %% "play-json" % playJsonVersion)

lazy val spray = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-spray")
  .settings(description := "Spray JSON library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies += "io.spray" %% "spray-json" % sprayJsonVersion)

lazy val msgpack = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-msgpack")
  .settings(description := "MsgPack library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies ++= Seq("org.json4s" %% "json4s-native" % json4sVersion,
    "org.velvia" %% "msgpack4s" % "0.6.0"))

// Templating libraries
lazy val handlebars = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-handlebars")
  .settings(description := "Handlebars templating library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies += "io.github.daviddenton" %% "handlebars-scala-fork" % handlebarsScalaForkVersion exclude("org.slf4j", "slf4j-simple"))

lazy val mustache = project
  .settings(baseSettings)
  .settings(moduleName := "fintrospect-mustache")
  .settings(description := "Mustache templating library support for Fintrospect")
  .dependsOn(core % "compile->test")
  .settings(libraryDependencies += "com.github.spullara.mustache.java" % "compiler" % mustacheCompilerVersion)

// other
lazy val examples = project.in(file("."))
  .settings(baseSettings)
  .enablePlugins(ScalaUnidocPlugin)
  .settings(moduleName := "fintrospect-examples")
  .aggregate(core, argonaut, circe, gson, jackson, json4s, play, spray, msgpack, handlebars, mustache)
  .dependsOn(core, argonaut, circe, gson, jackson, json4s, play, spray, msgpack, handlebars, mustache)
