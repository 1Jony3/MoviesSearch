package com.example.moviessearch

import android.app.Application
import com.example.moviessearch.model.data.di.appModule
import com.example.moviessearch.model.data.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule)
        }

    }
}


