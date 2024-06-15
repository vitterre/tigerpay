package com.tigerpay.analytics.model

sealed trait Period

object Period {
  case object LAST_WEEK extends Period
  case object LAST_MONTH extends Period
  case object LAST_THREE_MONTHS extends Period
}
