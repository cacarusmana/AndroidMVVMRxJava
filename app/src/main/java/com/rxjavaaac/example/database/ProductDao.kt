package com.rxjavaaac.example.database

import androidx.room.*
import com.rxjavaaac.example.model.TProduct
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author caca rusmana on 2019-09-26
 */

@Dao
interface ProductDao {

    @Query("select * from TProduct order by productName")
    fun getAll(): Single<MutableList<TProduct>>

    @Query("select * from TProduct WHERE productName LIKE :value OR price LIKE :value order by productName")
    fun findProducts(value: String): Single<MutableList<TProduct>>

    @Insert
    fun insert(tProduct: TProduct): Completable

    @Insert
    fun insertAll(products: List<TProduct>)

    @Update
    fun update(tProduct: TProduct): Completable

    @Delete
    fun delete(tProduct: TProduct): Completable

    @Query("delete from TProduct")
    fun deleteAll(): Completable
}