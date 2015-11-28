package store

import scala.collection.mutable.ArrayBuffer

object Key extends Enumeration {
  val Brie, Truffles, Strawberries, Champagne = Value
}

trait Product {
  def key: Key.Value
  def price: Double
}
case class Brie(key: Key.Value, price: Double) extends Product
case class Truffles(key: Key.Value, price: Double) extends Product
case class Strawberries(key: Key.Value, price: Double) extends Product
case class Champagne(key: Key.Value, price: Double) extends Product

case class Discount(key: Key.Value, quantity: Int, discount: Double)

case class Bundle(keys: Set[Key.Value], discount: Double)

case class Catalog(products: Set[Product], discounts: Set[Discount], bundles: Set[Bundle])
object Catalog {
  def apply(): Catalog = {
    Catalog(products, discounts, bundles)
  }

  private def products: Set[Product] = {
    val brie = Brie(Key.Brie, 5.00)
    val truffles = Truffles(Key.Truffles, 15.00)
    val strawberries = Strawberries(Key.Strawberries, 10.00)
    val champagne = Champagne(Key.Champagne, 30.00)
    Set[Product](brie, truffles, strawberries, champagne)
  }

  private def discounts: Set[Discount] = {
    val brie = Discount(Key.Brie, 2, 0.10)
    val truffles = Discount(Key.Truffles, 2, 0.10)
    val strawberries = Discount(Key.Strawberries, 2, 0.10)
    val champagne = Discount(Key.Champagne, 2, 0.10)
    Set[Discount](brie, truffles, strawberries, champagne)
  }

  private def bundles: Set[Bundle] = {
    val brieChampagne = Bundle(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries), 0.10)
    Set[Bundle](brieChampagne)
  }
}

case class Item(product: Product, quantity: Int)

case class Cart(catalog: Catalog, items: ArrayBuffer[Item] = ArrayBuffer[Item]())

class Store(catalog: Catalog) {
  def shop: Cart = Cart(catalog)

  def checkout(cart: Cart): Unit = {
  }
}