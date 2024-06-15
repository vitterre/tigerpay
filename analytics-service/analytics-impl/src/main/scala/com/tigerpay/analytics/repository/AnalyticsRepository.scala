package com.tigerpay.analytics.repository

import com.tigerpay.analytics.model.{CategoriesRecord, Currency, Period, TransfersRecord}
import zio._

import java.util.UUID

trait AnalyticsRepository {
  def findExpenseAndRevenue(senderUuid: UUID,
                            currency: Currency,
                            period: Period): UIO[List[List[CategoriesRecord]]]
}