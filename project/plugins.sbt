resolvers += "Flyway" at "https://davidmweber.github.io/flyway-sbt.repo"
addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.18")
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "latest.integration")
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "latest.integration")