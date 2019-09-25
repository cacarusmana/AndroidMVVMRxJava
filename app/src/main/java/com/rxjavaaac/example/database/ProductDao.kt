package com.rxjavaaac.example.database

import androidx.room.*
import com.rxjavaaac.example.model.TProduct

@Dao
interface ProductDao {

    @Query("select * from TProduct order by productName")
    fun getAll(): MutableList<TProduct>

    @Query("select * from TProduct WHERE productName LIKE :value OR price LIKE :value order by productName")
    fun findProducts(value: String): MutableList<TProduct>

    @Insert
    fun insert(tProduct: TProduct)

    @Update
    fun update(tProduct: TProduct)

    @Delete
    fun delete(tProduct: TProduct)

    @Query("delete from TProduct")
    fun deleteAll()
}