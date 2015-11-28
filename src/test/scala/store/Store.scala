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
  def apply(product: Key.Value, price: Double, quantity: Double): Double = {
    if (key == product && quantity >= threshold)
      quantity * (price * discount)
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

case class Cart(catalog: Catalog) {
  private val items: ArrayBuffer[Item] = ArrayBuffer[Item]()

  def add(item: Item): Unit = items += item

  def remove(item: Item): Unit = items -= item

  def checkout: Double = {
    val totalAmount = calculateTotalAmount
    val totalDiscountAmount = calculateDiscountAmount
    val totalBundlePercentage = calculateBundlePercentage
    val totalBundleAmount = totalAmount * totalBundlePercentage
    totalAmount - (totalDiscountAmount + totalBundleAmount)
  }

  private def calculateTotalAmount: Double = items.map(_.total).sum

  private def calculateDiscountAmount: Double = {
    val discounts = catalog.discounts
    var totalAmount = 0.0
    discounts.foreach { discount =>
      items.filter(_.product.key == discount.key).foreach { item =>
        totalAmount += discount.apply(item.product.key, item.product.price, item.quantity)
      }
    }
    totalAmount
  }

  private def calculateBundlePercentage: Double = {
    val bundles = catalog.bundles
    var totalPercentage = 0.0
    val products = items.map(_.product.key).toSet
    bundles.foreach { bundle =>
      totalPercentage += bundle.apply(products)
    }
    totalPercentage
  }
}

class Store(catalog: Catalog) {
  def shop: Cart = Cart(catalog)

  def checkout(cart: Cart): Double = {
    cart.checkout
  }
}