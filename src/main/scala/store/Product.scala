package store

enum ProductKey:
  case Brie, Truffles, Strawberries, Champagne

trait Product:
  def key: ProductKey
  def price: Double
final case class Brie(key: ProductKey, price: Double) extends Product
final case class Truffles(key: ProductKey, price: Double) extends Product
final case class Strawberries(key: ProductKey, price: Double) extends Product
final case class Champagne(key: ProductKey, price: Double) extends Product