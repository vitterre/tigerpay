package com.tigerpay.analytics.model

sealed trait Currency

object Currency {
  case object USD extends Currency
  case object EUR extends Currency
}
