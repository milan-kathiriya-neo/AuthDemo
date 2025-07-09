package com.example.authdemo.di

import com.example.authdemo.DatabaseDriverFactory
import com.example.authdemo.createDataStoreAndroid
import org.koin.dsl.module

val androidAppModule = module {
    single { DatabaseDriverFactory(get()) }

    single { createDataStoreAndroid(get()) }
}