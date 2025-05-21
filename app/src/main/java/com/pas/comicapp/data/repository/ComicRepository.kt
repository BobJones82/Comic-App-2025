package com.pas.comicapp.data.repository

import com.pas.comicapp.data.api.ComicApiService
import com.pas.comicapp.domain.model.Comic
import com.pas.comicapp.domain.model.toDomain
import javax.inject.Inject

/**
 * Repository class for handling comic-related operations.
 *
 * @property comicApiService
 */
class ComicRepository @Inject constructor(
    private val comicApiService: ComicApiService
) {
    suspend fun getComics(): List<Comic> {
        return comicApiService.getComics().map { it.toDomain() }
    }

    suspend fun getComicDetails(comicId: String): Comic {
        val allComics = comicApiService.getComics().map { it.toDomain() }

        return allComics.firstOrNull { it.id == comicId }
            ?: throw NoSuchElementException("Comic with ID $comicId not found.")
    }
}