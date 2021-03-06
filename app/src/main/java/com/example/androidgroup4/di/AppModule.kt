package com.example.androidgroup4.di

import com.example.androidgroup4.data.article.remote.ArticleApiService
import com.example.androidgroup4.data.doctor.remote.DoctorApiService
import com.example.androidgroup4.data.user.remote.UserApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://fsw-api-grup4.herokuapp.com/api/v1/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideUserService(moshi: Moshi): UserApiService {
        return Retrofit
            .Builder()
            .run {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                build()
            }.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleService(moshi: Moshi): ArticleApiService {
        return Retrofit
            .Builder()
            .run {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                build()
            }.create(ArticleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDoctorService(moshi: Moshi): DoctorApiService {
        return Retrofit
            .Builder()
            .run {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                build()
            }.create(DoctorApiService::class.java)
    }
}