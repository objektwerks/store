package store

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class Store(val catalog: Catalog) {
  def newShopper: Shopper = Shopper(Cart(catalog))

  def checkout(shopper: Shopper): Receipt = {
    shopper.cart.checkout(shopper.id, shopper.payment)
  }
}

object Store {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  def fillCart(cart: Cart): Unit = {
    val brie = Item(Brie(Key.Brie, 10.00), 2)
    val truffles = Item(Truffles(Key.Truffles, 20.00), 2)
    val strawberries = Item(Strawberries(Key.Strawberries, 20.00), 2)
    val champagne = Item(Champagne(Key.Champagne, 50.00), 2)
    cart.add(brie)
    cart.add(truffles)
    cart.add(strawberries)
    cart.add(champagne)
  }

  def createReceipts(store: Store, shoppers: Int): List[Future[Receipt]] = {
    val buffer: ListBuffer[Future[Receipt]] = mutable.ListBuffer[Future[Receipt]]()
    for (_ <- 1 to shoppers) {
      buffer += createReceipt(store)
    }
    buffer.toList
  }

  def createReceipt(store: Store): Future[Receipt] = {
    Future {
      val shopper = store.newShopper
      fillCart(shopper.cart)
      store.checkout(shopper)
    }
  }
}