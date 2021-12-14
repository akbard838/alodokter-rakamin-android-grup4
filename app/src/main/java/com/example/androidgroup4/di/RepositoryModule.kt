package com.example.androidgroup4.di

import com.example.androidgroup4.data.user.UserRepository
import com.example.androidgroup4.data.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(impl: UserRepositoryImpl): UserRepository

}