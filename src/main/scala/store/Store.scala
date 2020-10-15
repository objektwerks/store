package store

class Store(val catalog: Catalog) {
  def newShopper: Shopper = Shopper(Cart(catalog))

  def checkout(shopper: Shopper): Receipt = {
    shopper.cart.checkout(shopper.id, shopper.payment)
  }
}