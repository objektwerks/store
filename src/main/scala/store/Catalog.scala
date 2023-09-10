package store

import ProductKey.*

final case class Catalog(products: Set[Product],
                         discounts: Set[Discount],
                         bundles: Set[Bundle])

final case class Discount(key: ProductKey,
                          threshold: Int,
                          discount: Double):
  def price(product: ProductKey,
            price: Double,
            quantity: Double): Double =
    if key == product && quantity >= threshold then quantity * (price * discount)
    else 0.0

final case class Bundle(keys: Set[ProductKey],
                        discount: Double):
  def price(products: Set[ProductKey]): Double =
    if keys.intersect(products) == keys then discount else 0.0

object Catalog:
  def apply(): Catalog = Catalog(products, discounts, bundles)

  private def products: Set[Product] =
    val brie = Product(Brie, 10.00)
    val truffles = Product(Truffles, 20.00)
    val strawberries = Product(Strawberries, 20.00)
    val champagne = Product(Champagne, 50.00)
    Set[Product](brie, truffles, strawberries, champagne)

  private def discounts: Set[Discount] =
    val brie = Discount(Brie, 2, 0.10)
    val truffles = Discount(Truffles, 2, 0.10)
    val strawberries = Discount(Strawberries, 2, 0.10)
    val champagne = Discount(Champagne, 2, 0.10)
    Set[Discount](brie, truffles, strawberries, champagne)

  private def bundles: Set[Bundle] =
    Set[Bundle](
      Bundle(
        keys = Set( Champagne, Brie, Truffles, Strawberries ),
        discount = 0.10
      )
    )