package com.tigerpay.analytics.config

import zio._
import zio.config._
import zio.http.Server

final case class HttpConfig(port: Int, gracefulShutdownTimeout: Int)

object HttpConfig {

  val layer: ZLayer[Any, Config.Error, Server.Config] =
    ZLayer {
      for {
        rawConfig <- read(ApplicationConfig.config from ApplicationConfig.configProvider)
      } yield Server.Config.default
        .port(rawConfig.http.port)
        .gracefulShutdownTimeout(rawConfig.http.gracefulShutdownTimeout.seconds)
    }
}