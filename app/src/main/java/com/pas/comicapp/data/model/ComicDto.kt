package com.pas.comicapp.data.model

import com.google.gson.annotations.SerializedName

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
data class ComicDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("pageCount") val pageCount: Int?,
    @SerializedName("creators") val creators: List<String>?
)