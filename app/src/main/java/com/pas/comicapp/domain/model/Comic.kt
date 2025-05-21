package com.pas.comicapp.domain.model

/**
 * Data class represents the structure of
 * a single comic instance received from the API.
 *
 * @property id
 * @property title
 * @property description
 * @property imageUrl
 * @property pageCount
 * @property creators
 */
data class Comic(val id: String,
                 val title: String,
                 val description: String,
                 val imageUrl: String,
                 val pageCount: Int,
                 val creators: List<String>
)

/**
 * Extension function to convert ComicDto to Comic domain model.
 *
 * @return
 */
fun com.pas.comicapp.data.model.ComicDto.toDomain(): Comic {
    return Comic(
        id = this.id,
        title = this.title,
        description = this.description ?: "No description available.",
        imageUrl = this.imageUrl ?: "",
        pageCount = this.pageCount ?: 0,
        creators = this.creators ?: emptyList()
    )
}