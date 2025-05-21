package com.pas.comicapp.data.api

import com.pas.comicapp.data.model.ComicDto
import retrofit2.http.GET


/**
 * Interface defines the network endpoints for the Comic API.
 *
 */
interface ComicApiService {
    @GET("v3/f40078b6-78be-498c-b919-e6586f3be0c0")
    suspend fun getComics(): List<ComicDto>
}