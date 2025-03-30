package com.example.randomusersapp.di

import com.example.randomusersapp.data.repository.UserRepositoryImpl
import com.example.randomusersapp.domain.repository.UserRepository
import com.example.randomusersapp.network.RetrofitHelper
import com.example.randomusersapp.view.UserListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitHelper.getInstance() }
    single<UserRepository> { UserRepositoryImpl(get()) }
    viewModel { UserListViewModel(get()) }
}