package hello.springtx.unitTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UnitTest1 {
    @Test
    fun tset1() {
        // 준비
        val store = Store()
        store.addInventory(Product.SHAMPOO, 10)
        val customer = Customer()

        // 실행
        val success = customer.purchase(store, Product.SHAMPOO, 5)

        // 검증
        assertThat(success).isTrue
        assertThat(store.product[Product.SHAMPOO]).isEqualTo(5)
    }

    @Test
    fun test2() {
        // 준비
        val store = Store()
        store.addInventory(Product.SHAMPOO, 10)
        val customer = Customer()

        // 실행
        val success = customer.purchase(store, Product.SHAMPOO, 15)

        // 검증
        assertThat(success).isFalse
        assertThat(store.product[Product.SHAMPOO]).isEqualTo(10)
    }
}

class Store(
    val product: MutableMap<Product, Int> = mutableMapOf(Product.SHAMPOO to 0, Product.BOOK to 0)
) {
    fun addInventory(product: Product, count: Int) {
        this.product[product] = this.product[product]!! + count
    }
}

class Customer {
    fun purchase(store: Store, product: Product, count: Int): Boolean {
        return if (store.product[product]!! - count >= 0) {
            store.product[product] = store.product[product]!! - count
            true
        } else {
            false
        }
    }
}

enum class Product {
    SHAMPOO, BOOK
}