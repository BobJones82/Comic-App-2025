package com.pas.comicapp.di

import com.pas.comicapp.data.api.ComicApiService
import com.pas.comicapp.data.repository.ComicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideComicApiService(retrofit: Retrofit): ComicApiService {
        return retrofit.create(ComicApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideComicRepository(comicApiService: ComicApiService): ComicRepository {
        return ComicRepository(comicApiService)
    }
}