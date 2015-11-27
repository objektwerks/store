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

case class Discount(entry: Entry, quantity: Int, discount: Double, rule: (Entry, Int, Double) => Entry) {
  def apply: Entry = rule(entry, quantity, discount)
}

case class Bundle(entries: Set[Entry], rule: (Set[Entry]) => Set[Entry]) {
  def apply: Set[Entry] = rule(entries)
}

case class Catalog(entries: Set[Entry], discounts: Set[Discount], bundles: Set[Bundle])

case class Item(entry: Entry, quantity: Int)

case class Cart(items: ArrayBuffer[Item] = new ArrayBuffer[Item]())

case class Order()

case class Session(catalog: Catalog) {
  private val cart = Cart()

  def add(item: Item): Unit = cart.items += item

  def remove(item: Item): Unit = cart.items -= item

  def checkout: Order = {
    Order()
  }
}

object Store {
  private val catalog = Builder.catalog

  def shop: Session = Session(catalog)
}

object Builder {
  def catalog: Catalog = {
    Catalog(Set[Entry](), Set[Discount](), Set[Bundle]())
  }
}