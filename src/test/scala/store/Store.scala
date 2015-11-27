package store

trait Product
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

case class Bundle(products: Set[Product], price: Double)

case class Catalog(entries: Set[Entry], discounts: Set[Discount], bundles: Set[Bundle])

case class Cart(entries: Vector[Entry])

class Store(catalog: Catalog) {
  def checkout(cart: Cart): Double = 0.0
}