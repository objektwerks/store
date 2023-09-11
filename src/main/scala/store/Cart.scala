package store

import java.time.LocalDateTime

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

final case class Cart(catalog: Catalog):
  private val items: ListBuffer[Item] = mutable.ListBuffer[Item]()

  def add(item: Item): Unit = items += item

  def remove(item: Item): Unit = items -= item

  def checkout(shopper: Int, payment: String): Receipt =
    val totalAmount = calculateTotalAmount
    val totalDiscountAmount = calculateDiscountAmount
    val totalBundlePercentage = calculateBundlePercentage
    val totalBundleAmount = totalAmount * totalBundlePercentage
    val finalTotal = totalAmount - (totalDiscountAmount + totalBundleAmount)
    Receipt(shopper, payment, items.toList, totalAmount, totalDiscountAmount, totalBundlePercentage, totalBundleAmount, finalTotal)

  private def calculateTotalAmount: Double = items.map(_.total).sum

  private def calculateDiscountAmount: Double =
    val discounts = catalog.discounts
    var totalAmount = 0.0
    discounts.foreach { discount =>
      items.filter(_.product.key == discount.key).foreach { item =>
        totalAmount += discount.price(item.product.key, item.product.price, item.quantity.toDouble)
      }
    }
    totalAmount

  private def calculateBundlePercentage: Double =
    val bundles = catalog.bundles
    var totalPercentage = 0.0
    val products = items.map(_.product.key).toSet
    bundles.foreach { bundle => totalPercentage += bundle.price(products) }
    totalPercentage

final case class Item(product: Product, quantity: Int):
  def total: Double = product.price * quantity

final case class Receipt(shopper: Int,
                         payment: String,
                         items: List[Item],
                         totalAmount: Double,
                         totalDiscountAmount: Double,
                         totalBundlePercentage: Double,
                         totalBundleAmount: Double,
                         finalTotal: Double,
                         purchased: LocalDateTime = LocalDateTime.now()):
  override def toString: String =
    val builder = new StringBuilder()
    builder ++= s"Shopper: $shopper\n"
    builder ++= s"Payment: $payment\n"
    builder ++= s"Purchased: $purchased\n"
    items.foreach { item =>
      builder ++= s"Item ( product: ${item.product.key}, price: ${item.product.price}, quantity: ${item.quantity} )\n" 
    }
    builder ++= s"Total Amount: $totalAmount\n"
    builder ++= s"Total Discount Amount: $totalDiscountAmount\n"
    builder ++= s"Total Bundle Percentage: $totalBundlePercentage\n"
    builder ++= s"Total Bundle Amount: $totalBundleAmount\n"
    builder ++= s"Final Total: $finalTotal\n"
    builder.toString()