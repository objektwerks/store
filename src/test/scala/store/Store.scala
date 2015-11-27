package store

import scala.collection.mutable.ArrayBuffer

sealed trait Product
trait Apple extends Product
trait Banana extends Product
trait Grapes extends Product
trait Kiwi extends Product
trait Orange extends Product
trait Pear extends Product
trait Strawberries extends Product
trait Champagne extends Product

case class Price(price: Double)

case class Entry(product: Product, price: Price)

case class Discount(product: Product, quantity: Int, discount: Double)

case class Bundle(products: Set[Product], discount: Double)

case class Catalog(entries: Set[Entry], discounts: Set[Discount], bundles: Set[Bundle])

case class Item(entry: Entry, quantity: Int)

case class Cart(items: ArrayBuffer[Item] = new ArrayBuffer[Item]())

case class Receipt()

case class Session(catalog: Catalog) {
  private val cart = Cart()

  def add(item: Item): Unit = cart.items += item

  def remove(item: Item): Unit = cart.items -= item

  def checkout: Receipt = {
    Receipt()
  }
}

object Store {
  private val catalog = Builder.catalog

  def shop: Session = Session(catalog)
}

object Builder {
  def catalog: Catalog = Catalog(Set[Entry](), Set[Discount](), Set[Bundle]())
}