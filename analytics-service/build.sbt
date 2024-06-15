import Dependencies.dependencies

ThisBuild / version := "0.0.1-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"
ThisBuild / resolvers += Resolver.mavenLocal

lazy val main = Some("com.tigerpay.analytics.Main")

lazy val analyticsImpl = (project in file("analytics-impl"))
	.settings(
		name := "analytics-impl",
		libraryDependencies ++= dependencies,
    Compile / run / mainClass := main,
		assembly / mainClass := main,
		assembly / assemblyMergeStrategy := {
			case PathList("META-INF", xs @ _ *) => MergeStrategy.discard
			case x => MergeStrategy.first
		},
		scalacOptions += "-Ymacro-annotations"
	)

lazy val analytics = (project in file("."))
	.settings(
		name := "analytics-service"
	)
	.aggregate(analyticsImpl)

