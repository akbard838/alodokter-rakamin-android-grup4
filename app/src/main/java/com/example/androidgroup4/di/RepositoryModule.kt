package com.example.androidgroup4.di

import com.example.androidgroup4.data.article.ArticleRepository
import com.example.androidgroup4.data.article.ArticleRepositoryImpl
import com.example.androidgroup4.data.doctor.DoctorRepository
import com.example.androidgroup4.data.doctor.DoctorRepositoryImpl
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
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindArticleRepository(impl: ArticleRepositoryImpl): ArticleRepository

    @Binds
    abstract fun bindDoctorRepository(impl: DoctorRepositoryImpl): DoctorRepository

}