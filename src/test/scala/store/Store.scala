package store

case class Product(name: String)

case class Price(product: Product, amount: Double)

case class Discount(price: Price, discount: Double)

case class Basket(items: Vector[Product])

case class Store(products: Vector[Product], prices: Vector[Price], discounts: Vector[Discount]) {
  def checkout(basket: Basket *): Double = 0.0
}
