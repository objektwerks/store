package store

import org.scalatest.FunSuite

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Failure}

class StoreTest extends FunSuite {
  private implicit val ec = ExecutionContext.global

  test("discount") {
    val discount = Discount(Key.Brie, 2, 0.10)
    val discountedAmount = discount.apply(Key.Brie, 10.0, 2)
    val discountedTotal = (10.0 * 2) - discountedAmount
    assert(discountedAmount == 2.0)
    assert(discountedTotal == 18.0)
  }

  test("bundle") {
    val bundle = Bundle(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries), 0.10)
    val discountedPercentage = bundle.apply(Set(Key.Champagne, Key.Brie, Key.Truffles, Key.Strawberries))
    assert(discountedPercentage == 0.1)
  }

  test("cart") {
    val catalog = Catalog()
    val cart = Cart(catalog)

    val brie = Item(Brie(Key.Brie, 10.00), 2)
    val truffles = Item(Truffles(Key.Truffles, 20.00), 2)
    val strawberries = Item(Strawberries(Key.Strawberries, 20.00), 2)
    val champagne = Item(Champagne(Key.Champagne, 50.00), 2)
    cart.add(brie)
    cart.add(truffles)
    cart.add(strawberries)
    cart.add(champagne)

    val receipt = cart.checkout
    assert(receipt.totalAmount == 200.0)
    assert(receipt.totalDiscountAmount == 20.0)
    assert(receipt.totalBundlePercentage == 0.1)
    assert(receipt.totalBundleAmount == 20.0)
    assert(receipt.finalTotal == 160.0)
    println(receipt.print)
  }

  test("store") {
    val catalog = Catalog()
    val store = new Store(catalog)
    val futures = Seq(buildFutureSeq(store), buildFutureSeq(store), buildFutureSeq(store), buildFutureSeq(store))
    val future = Future.sequence(futures)
    future onComplete {
      case Success(receipt) =>
        assert(receipt != null)
        println(receipt)
      case Failure(failure) => throw failure
    }
  }

  def buildFutureSeq(store: Store) = {
    Future {
      val cart = store.shop
      val brie = Item(Brie(Key.Brie, 10.00), 2)
      val truffles = Item(Truffles(Key.Truffles, 20.00), 2)
      val strawberries = Item(Strawberries(Key.Strawberries, 20.00), 2)
      val champagne = Item(Champagne(Key.Champagne, 50.00), 2)
      cart.add(brie)
      cart.add(truffles)
      cart.add(strawberries)
      cart.add(champagne)
      store.checkout(cart)
    }
  }
}