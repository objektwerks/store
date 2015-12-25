package store

import org.scalatest.FunSuite

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class StoreTest extends FunSuite {
  private implicit val ec = ExecutionContext.global

  test("discount") {
    val discount = Discount(Key.Brie, 2, 0.10)
    val discountedAmount = discount.apply(Key.Brie, 10.0, 2)
    val discountedTotal = (10.0 * 2) - discountedAmount
    assert(discountedAmount == 2.0)
    assert(discountedTotal == 18.0)
  }

  test("bundle discount") {
    val bundle = Bundle(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries), 0.10)
    val discountedPercentage = bundle.apply(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries))
    assert(discountedPercentage == 0.1)
  }

  test("cart") {
    val catalog = Catalog()
    val cart = Cart(catalog)
    fillCart(cart)
    val shopper = Shopper(cart)
    val receipt = cart.checkout(shopper.id, shopper.payment)
    assert(receipt.totalAmount == 200.0)
    assert(receipt.totalDiscountAmount == 20.0)
    assert(receipt.totalBundlePercentage == 0.1)
    assert(receipt.totalBundleAmount == 20.0)
    assert(receipt.finalTotal == 160.0)
    println(s"\nCart Test -> $receipt")
  }

  test("store") {
    val catalog = Catalog()
    val store = new Store(catalog)
    val futures = createListOfFutureReceipt(store, shoppers = 100)
    val future = Future.sequence(futures)
    future onComplete {
      case Success(receipt) =>
        println(s"Store Test -> Number of receipts: ${receipt.length}.\n")
        assert(receipt.length == futures.length)
      case Failure(failure) => throw failure
    }
  }
}