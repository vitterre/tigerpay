package com.tigerpay.analytics.config

import zio._
import zio.config._

final case class SecurityConfig(secret: String)

object SecurityConfig {

  val layer: ZLayer[Any, Config.Error, SecurityConfig] =
    ZLayer {
      for {
        rawConfig <- read(ApplicationConfig.config from ApplicationConfig.configProvider)
      } yield SecurityConfig(rawConfig.security.secret)
    }
}
