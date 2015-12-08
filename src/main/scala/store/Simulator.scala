package store

import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Simulator extends App {
  implicit val ec = ExecutionContext.global

  val catalog = Catalog()
  val store = new Store(catalog)
  val futures: List[Future[Receipt]] = createListOfFutureReceipt(store, shoppers = 1000)
  val future: Future[List[Receipt]] = Future.sequence(futures)
  future onComplete {
    case Success(receipt) => println(s"*** Number of shoppers: ${futures.length}! ***")
    case Failure(failure) => println(s"*** Simulation failed: ${failure.getMessage} ***")
  }
  Await.ready(future, 3 seconds)
}