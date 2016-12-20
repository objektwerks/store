package store

import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Simulator extends App {
  implicit val ec = ExecutionContext.global

  val catalog = Catalog()
  val store = new Store(catalog)
  val receipts = Future.sequence( createReceipts(store, shoppers = 1000) )
  receipts onComplete {
    case Success(shoppers) =>
      println(s"*** Number of shoppers: ${shoppers.length}! ***")
      require(shoppers.length == 1000)
    case Failure(failure) => println(s"*** Simulation failed: ${failure.getMessage} ***")
  }
  Await.ready(receipts, 3 seconds)
}