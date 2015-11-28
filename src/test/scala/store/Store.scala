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

case class Discount(key: Key.Value, threshold: Int, discount: Double) {
  def apply(item: Item): Double = {
    val product = item.product
    val quantity = item.quantity
    if (key == product.key && quantity >= threshold)
      quantity * (product.price * discount)
    else 0.0
  }
}

case class Bundle(keys: Set[Key.Value], discount: Double) {
  def apply(products: Set[Key.Value]): Double = {
    if (keys.intersect(products) == keys) discount else 0.0
  }
}

case class Catalog(products: Set[Product], discounts: Set[Discount], bundles: Set[Bundle])
object Catalog {
  def apply(): Catalog = {
    Catalog(products, discounts, bundles)
  }

  private def products: Set[Product] = {
    val brie = Brie(Key.Brie, 10.00)
    val truffles = Truffles(Key.Truffles, 20.00)
    val strawberries = Strawberries(Key.Strawberries, 20.00)
    val champagne = Champagne(Key.Champagne, 50.00)
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

case class Item(product: Product, quantity: Int) {
  def total: Double = product.price * quantity
}

case class Cart(catalog: Catalog, items: ArrayBuffer[Item] = ArrayBuffer[Item]()) {
  def total: Double = items.map(_.total).sum
}

class Store(catalog: Catalog) {
  def shop: Cart = Cart(catalog)

  def checkout(cart: Cart): Double = {
    val total = cart.total
    var totalDiscountAmount = 0.0
    var totalBundlePercentage = 0.0

    val discounts = catalog.discounts
    val items = cart.items
    discounts.foreach { discount =>
      items.filter(_.product.key == discount.key).foreach { item =>
        totalDiscountAmount += discount.apply(item)
      }
    }

    val bundles = catalog.bundles
    val products = items.map(_.product.key).toSet
    bundles.foreach { bundle =>
      totalBundlePercentage += bundle.apply(products)
    }

    val totalBundleAmount = total * totalBundlePercentage
    total - (totalDiscountAmount + totalBundleAmount)
  }
}