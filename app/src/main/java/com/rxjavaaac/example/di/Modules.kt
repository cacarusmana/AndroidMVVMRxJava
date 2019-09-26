package com.rxjavaaac.example.di

import androidx.room.Room
import com.rxjavaaac.example.database.AppDatabase
import com.rxjavaaac.example.repository.ProductRepository
import com.rxjavaaac.example.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author caca rusmana on 2019-09-26
 */

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    single { get<AppDatabase>().productDao() }
}

val repositoryModule = module {
    single { ProductRepository(productDao = get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(productRepository = get()) }
}