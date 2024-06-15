package com.tigerpay.analytics

import com.tigerpay.analytics.config._
import com.tigerpay.analytics.repository.AnalyticsRepositoryJdbcImpl
import com.tigerpay.analytics.server.HttpServer
import com.tigerpay.analytics.service.AnalyticsServiceImpl
import zio._
import zio.http.Server

object Main extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    HttpServer
      .serve
      .provide(
        SecurityConfig.layer ++
          (HttpConfig.layer >>> Server.live) ++
            (ClickHouseConfig.layer >>>
              AnalyticsRepositoryJdbcImpl.layer >>>
                AnalyticsServiceImpl.layer)
      )
}
