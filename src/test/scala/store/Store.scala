package store

import java.time.LocalDateTime

import store.Rules.{MatchRule, QuantityRule, BundleRule, DiscountRule}

import scala.collection.mutable.ArrayBuffer

object Kinds extends Enumeration {
  val Brie, Gouda, Kiwi, Plum, Champagne = Value
}

sealed trait Product {
  def kind: Kinds.Value
  def price: Double
}
case class Brie(kind: Kinds.Value, price: Double) extends Product
case class Gouda(kind: Kinds.Value, price: Double) extends Product
case class Kiwi(kind: Kinds.Value, price: Double) extends Product
case class Plum(kind: Kinds.Value, price: Double) extends Product
case class Champagne(kind: Kinds.Value, price: Double) extends Product

case object Rules {
  type QuantityRule = (Int) => Boolean
  type MatchRule = (Set[Kinds.Value], Set[Product]) => Boolean
  type DiscountRule = (Product, Double) => Double
  type BundleRule = (Set[Product], Double) => Double

  def greaterThan: QuantityRule = (quantity: Int) => if (quantity > 1) true else false

  def contentMatch: MatchRule = (kinds: Set[Kinds.Value], products: Set[Product]) => {
    kinds == products.map(_.kind)
  }

  def standardDiscount: DiscountRule = (product: Product, discount: Double) => {
    product.price - (product.price * discount)
  }

  def standardBundleDiscount: BundleRule = (products: Set[Product], discount: Double) => {
    val total = products.map(_.price).sum
    total  - (total * discount)
  }
}

case class Discount(quantityRule: QuantityRule, discountRule: DiscountRule, product: Product, discount: Double, quantity: Int) {
  def apply: Option[Double] = if (quantityRule(quantity)) Some(discountRule(product, discount)) else None
}

case class Bundle(matchRule: MatchRule, bundleRule: BundleRule, kinds: Set[Kinds.Value], products: Set[Product], discount: Double) {
  def apply: Option[Double] = if (matchRule(kinds, products)) Some(bundleRule(products, discount)) else None
}

case class Catalog(products: Set[Product], discounts: Set[Discount], bundles: Set[Bundle])

case class Item(product: Product, quantity: Int)

case class Cart(items: ArrayBuffer[Item] = ArrayBuffer[Item]())

case class Detail(product: Product, price: Double, discounted: Double, quantity: Int) {
  def total: Double = discounted * quantity
}

case class Order(details: Vector[Detail], placed: LocalDateTime = LocalDateTime.now()) {
  def total: Double = details.map(detail => detail.total).sum
}

case class Session(catalog: Catalog) {
  private val cart = Cart()

  def add(item: Item): Unit = cart.items += item

  def remove(item: Item): Unit = cart.items -= item

  def checkout: Order = { // TODO
    Order(Vector[Detail]())
  }
}

class Store(catalog: Catalog) {
  def shop: Session = Session(catalog)
}

class Builder {
  import Rules._

  private val brie = Brie(Kinds.Brie, 5.00)
  private val gouda = Gouda(Kinds.Gouda, 4.00)
  private val kiwi = Kiwi(Kinds.Kiwi, 2.00)
  private val plum = Plum(Kinds.Plum, 1.00)
  private val champagne = Champagne(Kinds.Champagne, 30.00)

  def catalog: Catalog = {
    Catalog(products, discounts, bundles)
  }

  private def products: Set[Product] = Set[Product](brie, gouda, kiwi, plum, champagne)

  private def discounts: Set[Discount] = {
    val brieDiscount = Discount(greaterThan, standardDiscount, brie, 0.10, 1)
    val goudaDiscount = Discount(greaterThan, standardDiscount, gouda, 0.10, 1)
    val champagneDiscount = Discount(greaterThan, standardDiscount, champagne, 0.10, 1)
    Set[Discount](brieDiscount, goudaDiscount, champagneDiscount)
  }

  private def bundles: Set[Bundle] = {
    Set[Bundle](brieGoudaChampagne, kiwiPlumChampagne)
  }

  private def brieGoudaChampagne: Bundle = {
    val kinds = Set[Kinds.Value](Kinds.Brie, Kinds.Gouda, Kinds.Champagne)
    val products = Set[Product](brie, gouda, champagne)
    Bundle(contentMatch, standardBundleDiscount, kinds, products, 0.10)
  }

  private def kiwiPlumChampagne: Bundle = {
    val kinds = Set[Kinds.Value](Kinds.Kiwi, Kinds.Plum, Kinds.Champagne)
    val products = Set[Product](kiwi, plum, champagne)
    Bundle(contentMatch, standardBundleDiscount, kinds, products, 0.10)
  }
}