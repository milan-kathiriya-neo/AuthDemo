package com.example.authdemo.di

import com.example.authdemo.DatabaseDriverFactory
import org.koin.dsl.module

val androidAppModule = module {
    single { DatabaseDriverFactory(get()) }
}
