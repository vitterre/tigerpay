package com.tigerpay.analytics.server

import com.tigerpay.analytics.config.SecurityConfig
import com.tigerpay.analytics.model.{CategoriesRecord, CategoriesRequest}
import com.tigerpay.analytics.service.AnalyticsService
import pdi.jwt._
import zio._
import zio.http.Middleware.CorsConfig
import zio.http._
import zio.http.codec.Doc
import zio.http.codec.PathCodec._
import zio.http.endpoint._
import zio.http.endpoint.openapi.{OpenAPIGen, SwaggerUI}
import zio.json._
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec

import java.util.UUID
import scala.util.Try


object HttpServer {

  private val corsConfig: CorsConfig =
    CorsConfig()

  private val middlewares: Middleware[Any] =
    Middleware.cors(corsConfig) ++
      Middleware.debug ++
        Middleware.requestLogging()

  private def jwtDecode(token: String, key: String): Try[JwtClaim] =
    Jwt.decode(token, key, JwtAlgorithm.allHmac())

  private def bearerAuthWithContext: HandlerAspect[SecurityConfig, UUID] = {
    HandlerAspect.interceptIncomingHandler(Handler.fromFunctionZIO[Request] { request =>
      request.header(Header.Authorization) match {
        case Some(Header.Authorization.Bearer(token)) =>
          ZIO.serviceWithZIO[SecurityConfig] { security =>
            ZIO
              .fromTry(jwtDecode(token.value.asString, security.secret))
              .orElseFail(Response.unauthorized)
              .flatMap { claim =>
                ZIO
                  .fromOption(claim.issuer)
                  .mapBoth(_ => Response.unauthorized, UUID.fromString)
              }
              .map(u => (request, u))
          }

        case _ => ZIO.fail(Response.unauthorized)
      }
    })
  }
  
  private val basePath = "api" / "v1" / "analytics"
  private val auth = EndpointMiddleware.auth
  private val categoriesEndpoint =
    Endpoint(
      Method.POST / basePath / "categories")
      .in[CategoriesRequest]
      .out[CategoriesRecord]
      .out[List[List[CategoriesRecord]]] @@ auth

  private val openApi = OpenAPIGen.fromEndpoints(
    title = "Analytics API",
    version = "1.0",
    categoriesEndpoint
  )

  private val swaggerRoutes = SwaggerUI.routes(Root / "docs" / "openapi", openApi)

  private def app: Routes[SecurityConfig with AnalyticsService, Serializable] =
    Routes(
      Method.POST / basePath / "categories" ->
        bearerAuthWithContext ->
          handler { (profileUuid: UUID, request: Request) =>
            for {
              statisticsRequest <- request
                .body
                .to[CategoriesRequest]
                .orElseFail(Response.badRequest)
              statistics <- AnalyticsService.getExpenseAndRevenue(profileUuid, statisticsRequest)
            } yield Response.json(statistics.toJson)
          }
    )

  def serve: URIO[SecurityConfig with AnalyticsService with Server, Nothing] =
    Server
      .serve(
        ((app @@ middlewares) ++ swaggerRoutes)
          .sandbox
      )
}
