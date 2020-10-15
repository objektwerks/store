package store

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.language.postfixOps

class StoreTest extends AnyFunSuite with Matchers {
  private implicit val ec = ExecutionContext.global

  test("discount") {
    val discount = Discount(Key.Brie, 2, 0.10)
    val discountedAmount = discount.price(Key.Brie, 10.0, 2)
    val discountedTotal = (10.0 * 2) - discountedAmount
    discountedAmount shouldBe 2.0
    discountedTotal shouldBe 18.0
  }

  test("bundle discount") {
    val bundle = Bundle(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries), 0.10)
    val discountedPercentage = bundle.price(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries))
    discountedPercentage shouldBe 0.1
  }

  test("cart") {
    val catalog = Catalog()
    val cart = Cart(catalog)
    fillCart(cart)
    val shopper = Shopper(cart)
    val receipt = cart.checkout(shopper.id, shopper.payment)
    receipt.totalAmount shouldBe 200.0
    receipt.totalDiscountAmount shouldBe 20.0
    receipt.totalBundlePercentage shouldBe 0.1
    receipt.totalBundleAmount shouldBe 20.0
    receipt.finalTotal shouldBe 160.0
    println(s"\nCart Test -> $receipt")
  }

  test("store") {
    val catalog = Catalog()
    val store = new Store(catalog)
    val receipts = Future.sequence( createReceipts(store, shoppers = 100) )
    receipts onComplete {
      case Success(shoppers) =>
        println(s"Store Test -> Number of receipts: ${shoppers.length}.\n")
        shoppers.length shouldBe 100
      case Failure(failure) => throw failure
    }
    Await.result(receipts, 1 second)
  }
}