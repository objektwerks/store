package store

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

final case class Shopper(id: Int, payment: String, cart: Cart) extends scala.Product with Serializable

object Shopper {
  private val counter = new AtomicInteger(1)

  def apply(cart: Cart): Shopper = Shopper(counter.getAndIncrement(), UUID.randomUUID().toString, cart)
}