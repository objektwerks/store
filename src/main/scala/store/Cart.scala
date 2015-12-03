package store

import java.time.LocalDateTime
import java.util.UUID

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class Cart(catalog: Catalog) {
  private val items: ListBuffer[Item] = mutable.ListBuffer[Item]()

  def add(item: Item): Unit = {
    items += item
  }

  def remove(item: Item): Unit = {
    items -= item
  }

  def checkout: Receipt = {
    val totalAmount = calculateTotalAmount
    val totalDiscountAmount = calculateDiscountAmount
    val totalBundlePercentage = calculateBundlePercentage
    val totalBundleAmount = totalAmount * totalBundlePercentage
    val finalTotal = totalAmount - (totalDiscountAmount + totalBundleAmount)
    Receipt(items.toVector, totalAmount, totalDiscountAmount, totalBundlePercentage, totalBundleAmount, finalTotal)
  }

  private def calculateTotalAmount: Double = items.map(_.total).sum

  private def calculateDiscountAmount: Double = {
    val discounts = catalog.discounts
    var totalAmount = 0.0
    discounts.foreach { discount =>
      items.filter(_.product.key == discount.key).foreach { item =>
        totalAmount += discount.apply(item.product.key, item.product.price, item.quantity)
      }
    }
    totalAmount
  }

  private def calculateBundlePercentage: Double = {
    val bundles = catalog.bundles
    var totalPercentage = 0.0
    val products = items.map(_.product.key).toSet
    bundles.foreach { bundle =>
      totalPercentage += bundle.apply(products)
    }
    totalPercentage
  }
}

case class Item(product: Product, quantity: Int) {
  def total: Double = product.price * quantity
}

case class Receipt(items: Vector[Item],
                   totalAmount: Double,
                   totalDiscountAmount: Double,
                   totalBundlePercentage: Double,
                   totalBundleAmount: Double,
                   finalTotal: Double,
                   purchased: LocalDateTime = LocalDateTime.now(),
                   number: String = UUID.randomUUID().toString) {
  override def toString: String = {
    val builder = new StringBuilder()
    builder ++= s"Number: $number\n"
    builder ++= s"Purchased: $purchased\n"
    items.foreach{ item => builder ++= s"Item ( product: ${item.product.key}, price: ${item.product.price}, quantity: ${item.quantity} )\n" }
    builder ++= s"Total Amount: $totalAmount\n"
    builder ++= s"Total Discount Amount: $totalDiscountAmount\n"
    builder ++= s"Total Bundle Percentage: $totalBundlePercentage\n"
    builder ++= s"Total Bundle Amount: $totalBundleAmount\n"
    builder ++= s"Final Total: $finalTotal\n"
    builder.toString()
  }
}