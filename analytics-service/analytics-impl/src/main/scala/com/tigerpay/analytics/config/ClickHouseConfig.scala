package com.tigerpay.analytics.config

import com.clickhouse.jdbc._
import zio._
import zio.config._

import java.sql._
import java.util.Properties

final case class ClickHouseConfig(host: String,
                                  port: Int,
                                  user: String,
                                  password: String,
                                  database: String,
                                  url: String)

object ClickHouseConfig {

  val layer: ZLayer[Any, Exception, ClickHouseDataSource] =
    ZLayer {
      for {
        rawConfig <- read(ApplicationConfig.config from ApplicationConfig.configProvider)
        session <- ZIO.attempt {
          val props = new Properties()
          props.setProperty("user", rawConfig.clickhouse.user)
          props.setProperty("password", rawConfig.clickhouse.password)
          new ClickHouseDataSource(rawConfig.clickhouse.url, props)
        }.refineToOrDie[SQLException]
      } yield session
    }
}