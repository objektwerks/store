package store

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

import ProductKey.*

class Store(val catalog: Catalog):
  def newShopper: Shopper = Shopper(Cart(catalog))

  def checkout(shopper: Shopper): Receipt = shopper.cart.checkout(shopper.id, shopper.payment)

object Store:
  given ExecutionContext = ExecutionContext.global

  def fillCart(cart: Cart): Unit =
    cart.add( Item(Product(Brie, 10.00), 2) )
    cart.add( Item(Product(Truffles, 20.00), 2) )
    cart.add( Item(Product(Strawberries, 20.00), 2) )
    cart.add( Item(Product(Champagne, 50.00), 2) )

  def createReceipts(store: Store, shoppers: Int): List[Future[Receipt]] =
    val buffer: ListBuffer[Future[Receipt]] = mutable.ListBuffer[Future[Receipt]]()
    for (_ <- 1 to shoppers) {
      buffer += createReceipt(store)
    }
    buffer.toList

  def createReceipt(store: Store): Future[Receipt] =
    Future {
      val shopper = store.newShopper
      fillCart(shopper.cart)
      store.checkout(shopper)
    }