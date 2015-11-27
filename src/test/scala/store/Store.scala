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

case class Price(amount: Double)

case class Entry(product: Product, price: Price)

case class Discount(rule: (Entry, Int, Double) => Entry, entry: Entry, quantity: Int, discount: Double) {
  def apply: Entry = rule(entry, quantity, discount)
}

case class Bundle(rule: (Set[Entry]) => Set[Entry], entries: Set[Entry]) {
  def apply: Set[Entry] = rule(entries)
}

case class Catalog(entries: Set[Entry], discounts: Set[Discount], bundles: Set[Bundle])

case class Item(entry: Entry, quantity: Int)

case class Cart(items: ArrayBuffer[Item] = ArrayBuffer[Item]())

case class Detail(product: Product, price: Price, discount: Price)

case class Order(details: ArrayBuffer[Detail] = ArrayBuffer[Detail]()) {
  def add(detail: Detail): Unit = details += detail

  def total: Double = details.map(detail => detail.price.amount).sum
}

case class Session(catalog: Catalog) {
  private val cart = Cart()

  def add(item: Item): Unit = cart.items += item

  def remove(item: Item): Unit = cart.items -= item

  def checkout: Order = {
    Order()
  }
}

case object Store {
  private val catalog = Builder.catalog

  def shop: Session = Session(catalog)
}

case object Builder {
  def catalog: Catalog = {
    Catalog(Set[Entry](), Set[Discount](), Set[Bundle]())
  }
}