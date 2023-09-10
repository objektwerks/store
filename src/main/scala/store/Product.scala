package store

enum ProductKey:
  case Brie, Truffles, Strawberries, Champagne

final case class Product(key: ProductKey, price: Double)