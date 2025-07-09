package com.example.authdemo.di

import com.example.authdemo.createDatabase
import com.example.authdemo.home.HomeViewModel
import com.example.authdemo.login.LoginViewModel
import com.example.authdemo.registration.RegistrationViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule: Module = module {
    single { createDatabase(get()) } // SQLDelight DB instance

    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
}