package store

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

case class Shopper(id: Int, payment: String, cart: Cart)

object Shopper {
  private val counter = new AtomicInteger(1)

  def apply(cart: Cart): Shopper = Shopper(counter.getAndIncrement(), UUID.randomUUID().toString, cart)
}