package store

import java.util.UUID

case class Shopper(name: String = UUID.randomUUID().toString, payment: String = UUID.randomUUID().toString)

class Store(catalog: Catalog) {
  def shop(shopper: Shopper): (Shopper, Cart) = (shopper, Cart(catalog))

  def checkout(shopperWithCart: (Shopper, Cart)): Receipt = {
    val (shopper, cart) = shopperWithCart
    cart.checkout(shopper)
  }
}