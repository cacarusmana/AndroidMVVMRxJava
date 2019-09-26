package com.rxjavaaac.example.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rxjavaaac.example.model.Converters
import com.rxjavaaac.example.model.TProduct

/**
 * @author caca rusmana on 2019-09-26
 */

@Database(entities = [TProduct::class], version = AppDatabase.DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        const val DB_NAME = "alfamet.db"
        const val DB_VERSION = 1
    }
}