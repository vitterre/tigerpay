package com.tigerpay.analytics.repository

import com.clickhouse.jdbc._
import com.tigerpay.analytics.model.Period.{LAST_MONTH, LAST_THREE_MONTHS, LAST_WEEK}
import com.tigerpay.analytics.model.{CategoriesRecord, Currency, Period, TransfersRecord}
import zio._

import java.sql.ResultSet
import java.util.UUID

final case class AnalyticsRepositoryJdbcImpl(dataSource: ClickHouseDataSource) extends AnalyticsRepository {


  //language=clickhouse
  private val findExpenseLastWeek =
    """
      |select category,
      |       sum(amount) as totalTransferred
      |from
      |  clickhouse_transfers_data_mart.transfers
      |where
      |  senderProfileUuid == ? and
      |  currency == ? and
      |  toDate(issuedAt) between addDays(toDate(now()), -7) and toDate(now())
      |group by
      |  category
      |order by
      |  totalTransferred desc;
      |""".stripMargin

  //language=clickhouse
  private val findExpenseLastMonth =
    """
      |select category,
      |       sum(amount) as totalTransferred
      |from
      |  clickhouse_transfers_data_mart.transfers
      |where
      |  senderProfileUuid == ? and
      |  currency == ? and
      |  toDate(issuedAt) between addMonths(toDate(now()), -1) and toDate(now())
      |group by
      |  category
      |order by
      |  totalTransferred desc;
      |""".stripMargin

  //language=clickhouse
  private val findExpenseLastThreeMonths =
    """
      |select category,
      |       sum(amount) as totalTransferred
      |from
      |  clickhouse_transfers_data_mart.transfers
      |where
      |  senderProfileUuid == ? and
      |  currency == ? and
      |  toDate(issuedAt) between addMonths(toDate(now()), -3) and toDate(now())
      |group by
      |  category
      |order by
      |  totalTransferred desc;
      |""".stripMargin

  //language=clickhouse
  private val findRevenueLastWeek =
    """
      |select category,
      |       sum(amount) as totalTransferred
      |from
      |  clickhouse_transfers_data_mart.transfers
      |where
      |  receiverProfileUuid == ? and
      |  currency == ? and
      |  toDate(issuedAt) between addDays(toDate(now()), -7) and toDate(now())
      |group by
      |  category
      |order by
      |  totalTransferred desc;
      |""".stripMargin

  //language=clickhouse
  private val findRevenueLastMonth =
    """
      |select category,
      |       sum(amount) as totalTransferred
      |from
      |  clickhouse_transfers_data_mart.transfers
      |where
      |  receiverProfileUuid == ? and
      |  currency == ? and
      |  toDate(issuedAt) between addMonths(toDate(now()), -1) and toDate(now())
      |group by
      |  category
      |order by
      |  totalTransferred desc;
      |""".stripMargin

  //language=clickhouse
  private val findRevenueLastThreeMonths =
    """
      |select category,
      |       sum(amount) as totalTransferred
      |from
      |  clickhouse_transfers_data_mart.transfers
      |where
      |  receiverProfileUuid == ? and
      |  currency == ? and
      |  toDate(issuedAt) between addMonths(toDate(now()), -3) and toDate(now())
      |group by
      |  category
      |order by
      |  totalTransferred desc;
      |""".stripMargin


  private def mapToList(rs: ResultSet): List[CategoriesRecord] =
    Iterator
      .continually(rs)
      .takeWhile(_.next())
      .map { rs =>
        CategoriesRecord(
          rs.getString("category"),
          BigDecimal(rs.getString("totalTransferred"))
        )
      }.toList

  private def executeFindQuery(connection: ClickHouseConnection,
                             profileUuid: UUID,
                             currency: String,
                             query: String): UIO[List[CategoriesRecord]] =
    ZIO.succeed {
      val statement = connection.prepareStatement(query)
      statement.setObject(1, profileUuid)
      statement.setString(2, currency)
      mapToList(statement.executeQuery())
    }

  private def findExpense(connection: ClickHouseConnection,
                          profileUuid: UUID,
                          currency: Currency,
                          period: Period): UIO[List[CategoriesRecord]] =
    executeFindQuery(connection, profileUuid, currency.toString, period match {
      case LAST_WEEK => findExpenseLastWeek
      case LAST_MONTH => findExpenseLastMonth
      case LAST_THREE_MONTHS => findExpenseLastThreeMonths
    })

  private def findRevenue(connection: ClickHouseConnection,
                          profileUuid: UUID,
                          currency: Currency,
                          period: Period): UIO[List[CategoriesRecord]] =
    executeFindQuery(connection, profileUuid, currency.toString, period match {
      case LAST_WEEK => findRevenueLastWeek
      case LAST_MONTH => findRevenueLastMonth
      case LAST_THREE_MONTHS => findRevenueLastThreeMonths
    })

  override def findExpenseAndRevenue(senderUuid: UUID,
                                     currency: Currency,
                                     period: Period): UIO[List[List[CategoriesRecord]]] =
    for {
      connection <- ZIO.succeed(dataSource.getConnection)
      expense    <- findExpense(connection, senderUuid, currency, period)
      revenue    <- findRevenue(connection, senderUuid, currency, period)
    } yield List(expense, revenue)
}

object AnalyticsRepositoryJdbcImpl {

  val layer: ZLayer[ClickHouseDataSource, Nothing, AnalyticsRepository] =
    ZLayer {
      for {
        session <- ZIO.service[ClickHouseDataSource]
      } yield AnalyticsRepositoryJdbcImpl(session)
    }
}
