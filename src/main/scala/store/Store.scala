package store

class Store(catalog: Catalog) {
  def shop: Shopper = Shopper(Cart(catalog))

  def checkout(shopper: Shopper): Receipt = {
    shopper.cart.checkout(shopper.id, shopper.payment)
  }
}