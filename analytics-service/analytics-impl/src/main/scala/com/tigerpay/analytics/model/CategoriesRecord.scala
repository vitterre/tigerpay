package com.tigerpay.analytics.model

import zio.json._
import zio.schema._
import zio.schema.annotation.description

case class CategoriesRecord(@description("Category transfers aggregated by") category: String,
                            @description("Total sum of all transfers during specified period") total: BigDecimal)

object CategoriesRecord {
  implicit val schema: Schema[CategoriesRecord] =
    DeriveSchema.gen[CategoriesRecord]
  implicit val jsonCodec: JsonCodec[CategoriesRecord] =
    zio.schema.codec.JsonCodec.jsonCodec(schema)
  implicit val jsonEncoder: JsonEncoder[CategoriesRecord] =
    DeriveJsonEncoder.gen[CategoriesRecord]
  implicit val jsonDecoder: JsonDecoder[CategoriesRecord] =
    DeriveJsonDecoder.gen[CategoriesRecord]

}
