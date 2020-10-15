package store

final case class Catalog(products: Set[Product], discounts: Set[Discount], bundles: Set[Bundle])

final case class Discount(key: Key.Value, threshold: Int, discount: Double) {
  def price(product: Key.Value, price: Double, quantity: Double): Double = {
    if (key == product && quantity >= threshold)
      quantity * (price * discount)
    else 0.0
  }
}

final case class Bundle(keys: Set[Key.Value], discount: Double) {
  def price(products: Set[Key.Value]): Double = {
    if (keys.intersect(products) == keys) discount else 0.0
  }
}

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
    Set[Bundle](Bundle(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries), 0.10))
  }
}