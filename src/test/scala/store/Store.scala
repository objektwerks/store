package store

import java.time.LocalDateTime

case class Product(name: String)

case class Price(product: Product, amount: Double, date: LocalDateTime = LocalDateTime.now())

case class Discount(price: Price, discount: Double, date: LocalDateTime = LocalDateTime.now())

case class Basket(items: Vector[Product], date: LocalDateTime = LocalDateTime.now())

case class Store(products: Vector[Product], prices: Vector[Price], discounts: Vector[Discount]) {
  def checkout(basket: Basket *): Double = 0.0
}
