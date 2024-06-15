package com.tigerpay.analytics.model

import zio.schema._

case class CategoriesRequest(currency: Currency, period: Period)

object CategoriesRequest {
  implicit val schema: Schema[CategoriesRequest] =
    DeriveSchema.gen
}
