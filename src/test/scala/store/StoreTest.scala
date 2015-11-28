package store

import org.scalatest.FunSuite

class StoreTest extends FunSuite{
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

  }
}