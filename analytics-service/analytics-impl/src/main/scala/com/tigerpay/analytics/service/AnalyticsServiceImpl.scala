package com.tigerpay.analytics.service

import com.tigerpay.analytics.model._
import com.tigerpay.analytics.repository.AnalyticsRepository
import zio._

import java.util.UUID

final case class AnalyticsServiceImpl(analyticsRepository: AnalyticsRepository) extends AnalyticsService {

  override def getExpenseAndRevenue(senderUuid: UUID,
                                    categoriesRequest: CategoriesRequest): UIO[List[List[CategoriesRecord]]] =
    for {
      data <- analyticsRepository.findExpenseAndRevenue(
        senderUuid,
        categoriesRequest.currency,
        categoriesRequest.period
      )
    } yield data
}

object AnalyticsServiceImpl {

  val layer: ZLayer[AnalyticsRepository, Nothing, AnalyticsService] =
    ZLayer {
      for {
        analyticsRepository <- ZIO.service[AnalyticsRepository]
      } yield AnalyticsServiceImpl(analyticsRepository)
    }
}
