package store

object Key extends Enumeration {
  val Brie, Truffles, Strawberries, Champagne = Value
}

trait Product {
  def key: Key.Value
  def price: Double
}

case class Brie(key: Key.Value, price: Double) extends Product

case class Truffles(key: Key.Value, price: Double) extends Product

case class Strawberries(key: Key.Value, price: Double) extends Product

case class Champagne(key: Key.Value, price: Double) extends Product