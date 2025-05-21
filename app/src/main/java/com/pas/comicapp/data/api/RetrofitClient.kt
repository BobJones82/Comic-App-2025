package com.pas.comicapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit client for making network requests to the Comic API.
 */
object RetrofitClient {

    private const val BASE_URL = "https://run.mocky.io"

    val instance: ComicApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComicApiService::class.java)
    }
}