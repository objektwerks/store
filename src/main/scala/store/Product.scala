package store

object Key extends Enumeration:
  val Brie, Truffles, Strawberries, Champagne = Value

trait Product:
  def key: Key.Value
  def price: Double
final case class Brie(key: Key.Value, price: Double) extends Product
final case class Truffles(key: Key.Value, price: Double) extends Product
final case class Strawberries(key: Key.Value, price: Double) extends Product
final case class Champagne(key: Key.Value, price: Double) extends Product