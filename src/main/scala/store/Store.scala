package store

class Store(catalog: Catalog) {
  def shop: Cart = Cart(catalog)

  def checkout(cart: Cart): Receipt = {
    cart.checkout
  }
}