name := "JPS_Dots"

version := "0.1"

scalaVersion := "2.12.6"

unmanagedClasspath in Compile += baseDirectory.value / "src/resources"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.4"
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
