import sbt.*

object Dependencies {

  lazy val zioVersion = "2.1.1"
  lazy val zioConfigVersion = "4.0.2"
  lazy val zioHttpVersion = "3.0.0-RC8"
  lazy val zioSchemaVersion = "1.2.0"
  lazy val zioJsonVersion = "0.6.2"
  lazy val zioJdbcVersion = "0.1.2"
  lazy val clickHouseJdbcVersion = "0.6.0-patch4"

  lazy val dependencies: Seq[ModuleID] = Seq(
    "dev.zio"        %% "zio"                 % zioVersion,
    "dev.zio"        %% "zio-macros"          % zioVersion,
    "dev.zio"        %% "zio-config"          % zioConfigVersion,
    "dev.zio"        %% "zio-config-magnolia" % zioConfigVersion,
    "dev.zio"        %% "zio-config-yaml"     % zioConfigVersion,
    "dev.zio"        %% "zio-config-typesafe" % zioConfigVersion,
    "dev.zio"        %% "zio-config-refined"  % zioConfigVersion,
    "dev.zio"        %% "zio-http"            % zioHttpVersion,
    "dev.zio"        %% "zio-schema"          % zioSchemaVersion,
    "dev.zio"        %% "zio-schema-json"     % zioSchemaVersion,
    "dev.zio"        %% "zio-json"            % zioJsonVersion,
    "com.github.jwt-scala"              %% "jwt-zio-json"        % "10.0.1",
    "dev.zio"        %% "zio-jdbc"            % zioJdbcVersion,
    "com.clickhouse" %  "clickhouse-jdbc"     % clickHouseJdbcVersion,
    "org.lz4"                           %  "lz4-java"            % "1.8.0",
    "org.apache.httpcomponents.core5"   %  "httpcore5"           % "5.2.4",
    "org.apache.httpcomponents.client5" %  "httpclient5"         % "5.3.1",
  )
}
