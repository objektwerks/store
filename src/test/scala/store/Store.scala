package store

case class Product(name: String, price: Double)

case class Discount(product: Product, discount: Double)

case class Basket(products: Vector[Product])

case class Store(products: Vector[Product], discounts: Vector[Discount]) {
  def checkout(basket: Basket *): Double = 0.0
}