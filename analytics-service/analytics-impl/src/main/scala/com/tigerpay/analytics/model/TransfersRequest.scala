package com.tigerpay.analytics.model

import zio.schema._

case class TransfersRequest(currency: Currency, period: Period)

object TransfersRequest {
  implicit val schema: Schema[TransfersRequest] =
    DeriveSchema.gen
}
