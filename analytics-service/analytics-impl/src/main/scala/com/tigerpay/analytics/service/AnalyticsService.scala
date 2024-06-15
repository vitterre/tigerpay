package com.tigerpay.analytics.service

import com.tigerpay.analytics.model._
import zio._

import java.util.UUID

trait AnalyticsService {
  def getExpenseAndRevenue(senderUuid: UUID,
                           categoriesRequest: CategoriesRequest): UIO[List[List[CategoriesRecord]]]
}

object AnalyticsService {
  def getExpenseAndRevenue(senderUuid: UUID,
                           categoriesRequest: CategoriesRequest): URIO[AnalyticsService, List[List[CategoriesRecord]]] =
    ZIO.serviceWithZIO[AnalyticsService](_.getExpenseAndRevenue(senderUuid, categoriesRequest))
}
