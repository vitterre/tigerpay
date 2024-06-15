package com.tigerpay.analytics.config

import zio._
import zio.config.magnolia.deriveConfig
import zio.config.yaml._

import scala.io.Source.fromResource

final case class ApplicationConfig(security: SecurityConfig, http: HttpConfig, clickhouse: ClickHouseConfig)

object ApplicationConfig {

  val config: Config[ApplicationConfig] =
    deriveConfig[ApplicationConfig]

  val configProvider: ConfigProvider =
    ConfigProvider.fromYamlReader(
      fromResource("application.yaml").reader
    )
}