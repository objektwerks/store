package store

case class Product(name: String)

case class Price(product: Product, amount: Double)

case class Discount(price: Price, discount: Option[Double])

case class Basket(items: Vector[Discount]) {
  def calculate: Vector[Double] = items.map(d => d.price.amount * d.discount.getOrElse(1.0))
}

case class Inventory(product: Product, stock: Int)

class Clerk {
  def checkout(basket: Basket): Double = basket.calculate.sum
}

case class Store(inventory: Inventory, clerk: Clerk)

case class Shopper(store: Store) {
  def checkout(basket: Basket): Double = store.clerk.checkout(Basket(Vector[Discount]()))
}