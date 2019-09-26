package com.rxjavaaac.example.repository

import com.rxjavaaac.example.database.ProductDao
import com.rxjavaaac.example.model.TProduct
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author caca rusmana on 2019-09-26
 */
class ProductRepository(private val productDao: ProductDao) {


    fun initData(): Completable {
        return Completable.create { emitter ->
            val products: MutableList<TProduct> = mutableListOf()

            repeat(1_000) {
                products.add(
                    TProduct(
                        productName = "product $it",
                        price = (100 * it).toBigDecimal()
                    )
                )
            }

            productDao.insertAll(products)
            emitter.onComplete()
        }
    }

    fun getProducts(value: String): Single<MutableList<TProduct>> = if (value == "") {
        productDao.getAll()
    } else {
        productDao.findProducts("%$value%")
    }

    fun save(product: TProduct): Completable = if (product.id == 0) {
        productDao.insert(product)
    } else {
        productDao.update(product)
    }

    fun deleteAll(): Completable = productDao.deleteAll()

    fun delete(product: TProduct): Completable = productDao.delete(product)
}