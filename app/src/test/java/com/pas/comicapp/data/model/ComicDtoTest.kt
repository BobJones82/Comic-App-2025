package com.pas.comicapp.data.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ComicDtoTest {

 @Test
 fun `ComicDto can be instantiated with all non-nullable fields`() {
  val comicDto = ComicDto(
   id = "test_001",
   title = "A Test Title",
   description = "A detailed description.",
   imageUrl = "http://example.com/image.jpg",
   pageCount = 48,
   creators = listOf("Creator A", "Creator B")
  )

  assertThat(comicDto.id).isEqualTo("test_001")
  assertThat(comicDto.title).isEqualTo("A Test Title")
  assertThat(comicDto.description).isEqualTo("A detailed description.")
  assertThat(comicDto.imageUrl).isEqualTo("http://example.com/image.jpg")
  assertThat(comicDto.pageCount).isEqualTo(48)
  assertThat(comicDto.creators).containsExactly("Creator A", "Creator B")
 }

 @Test
 fun `ComicDto can be instantiated with nullable fields as null`() {
  val comicDto = ComicDto(
   id = "test_002",
   title = "Another Title",
   description = null,
   imageUrl = null,
   pageCount = null,
   creators = null
  )

  assertThat(comicDto.id).isEqualTo("test_002")
  assertThat(comicDto.title).isEqualTo("Another Title")
  assertThat(comicDto.description).isNull()
  assertThat(comicDto.imageUrl).isNull()
  assertThat(comicDto.pageCount).isNull()
  assertThat(comicDto.creators).isNull()
 }

 @Test
 fun `ComicDto instances with same values are equal`() {
  val comicDto1 = ComicDto(
   id = "test_003",
   title = "Equal Title",
   description = "Same description",
   imageUrl = "url.jpg",
   pageCount = 20,
   creators = listOf("Creator X")
  )
  val comicDto2 = ComicDto(
   id = "test_003",
   title = "Equal Title",
   description = "Same description",
   imageUrl = "url.jpg",
   pageCount = 20,
   creators = listOf("Creator X")
  )

  assertThat(comicDto1).isEqualTo(comicDto2)
  assertThat(comicDto1.hashCode()).isEqualTo(comicDto2.hashCode())
 }

 @Test
 fun `ComicDto instances with different IDs are not equal`() {
  val comicDto1 = ComicDto(id = "test_004a", title = "Title", description =
   null, imageUrl = null, pageCount = null, creators = null)
  val comicDto2 = ComicDto(id = "test_004b", title = "Title", description =
   null, imageUrl = null, pageCount = null, creators = null)

  assertThat(comicDto1).isNotEqualTo(comicDto2)
 }

 @Test
 fun `ComicDto copy function works correctly`() {
  val originalComic = ComicDto(
   id = "original_id",
   title = "Original Title",
   description = "Original Desc",
   imageUrl = "original.jpg",
   pageCount = 100,
   creators = listOf("Original Creator")
  )

  val copiedComic = originalComic.copy(title = "New Title", pageCount = 101)

  assertThat(copiedComic.id).isEqualTo("original_id")
  assertThat(copiedComic.title).isEqualTo("New Title")
  assertThat(copiedComic.description).isEqualTo("Original Desc")
  assertThat(copiedComic.pageCount).isEqualTo(101)
  assertThat(copiedComic.creators).containsExactly("Original Creator")

  assertThat(copiedComic).isNotEqualTo(originalComic)
 }

 @Test
 fun `ComicDto toString includes relevant fields`() {
  val comicDto = ComicDto(
   id = "test_005",
   title = "ToString Test",
   description = null,
   imageUrl = null,
   pageCount = null,
   creators = null
  )
  val expectedToStringPart = "ComicDto(id=test_005, title=ToString Test"

  assertThat(comicDto.toString()).contains(expectedToStringPart)
 }
}