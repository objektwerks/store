package store

case class Product(name: String, price: Double, discount: Double = 0.0)

case class Basket(products: Vector[Product])

case class Store(products: Vector[Product]) {
  def checkout(basket: Basket *): Double = 0.0
}