package store

import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration.*
import scala.language.postfixOps
import scala.util.{Failure, Success}

import Store.*

@main
def runStore() =
  given ExecutionContext = ExecutionContext.global

  val catalog = Catalog()
  val store = Store(catalog)
  val receipts = Future.sequence( createReceipts(store, shoppers = 1000) )
  receipts onComplete {
    case Success(shoppers) => println(s"*** Number of shoppers: ${shoppers.length}! ***")
    case Failure(failure) => println(s"*** Simulation failed: ${failure.getMessage} ***")
  }
  Await.ready(receipts, 3 seconds)