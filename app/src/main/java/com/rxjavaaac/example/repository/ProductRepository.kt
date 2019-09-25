package com.rxjavaaac.example.repository

import com.rxjavaaac.example.database.ProductDao
import com.rxjavaaac.example.model.TProduct
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProductRepository(private val productDao: ProductDao) {


    fun initData(): Completable {
        return Completable.create { emitter ->
            repeat(2_000) {
                val product =
                    TProduct(productName = "product $it", price = (100 * it).toBigDecimal())
                productDao.insert(product)
                Timber.i("loop at $it")
            }

            emitter.onComplete()
        }
    }

    fun getAll(): Single<MutableList<TProduct>> {
        return Single.create { emitter ->
            emitter.onSuccess(productDao.getAll())
        }
    }

    fun findProducts(value: String): Single<MutableList<TProduct>> {
        return Single.create { emitter ->
            emitter.onSuccess(productDao.findProducts("%$value%"))
        }
    }

    fun save(product: TProduct): Completable {
        return Completable.create { emitter ->
            if (product.id == 0)
                productDao.insert(product)
            else
                productDao.update(product)

            emitter.onComplete()
        }
    }

    fun deleteAll(): Completable {
        return Completable.create { emitter ->
            productDao.deleteAll()
            emitter.onComplete()
        }
    }

    fun delete(product: TProduct): Completable {
        return Completable.create { emitter ->
            productDao.delete(product)
            emitter.onComplete()
        }
    }
}