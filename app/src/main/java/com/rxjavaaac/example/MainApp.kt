package com.rxjavaaac.example

import android.app.Application
import com.rxjavaaac.example.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * @author caca rusmana on 2019-09-26
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@MainApp)
            modules(arrayListOf(databaseModule, repositoryModule, viewModelModule))
        }

    }


}