package com.pas.comicapp.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ComicTest {

 @Test
 fun `Comic can be instantiated and properties accessed correctly`() {
  val comic = Comic(
   id = "comic_id_001",
   title = "The Amazing Test Comic",
   description = "A fantastic comic used for testing purposes.",
   imageUrl = "http://example.com/test_comic.jpg",
   pageCount = 64,
   creators = listOf("Test Writer", "Test Artist")
  )

  assertThat(comic.id).isEqualTo("comic_id_001")
  assertThat(comic.title).isEqualTo("The Amazing Test Comic")
  assertThat(comic.description).isEqualTo("A fantastic comic used for testing purposes.")
  assertThat(comic.imageUrl).isEqualTo("http://example.com/test_comic.jpg")
  assertThat(comic.pageCount).isEqualTo(64)
  assertThat(comic.creators).containsExactly("Test Writer", "Test Artist")
  assertThat(comic.creators).hasSize(2)
 }

 @Test
 fun `Comic handles empty list for creators correctly`() {
  val comic = Comic(
   id = "comic_id_002",
   title = "Comic with No Creators",
   description = "This comic has no credited creators.",
   imageUrl = "http://example.com/no_creators.jpg",
   pageCount = 28,
   creators = emptyList()
  )

  assertThat(comic.creators).isEmpty()
  assertThat(comic.creators).hasSize(0)
 }

 @Test
 fun `Comic instances with same values are equal`() {
  val comic1 = Comic(
   id = "comic_id_003",
   title = "Equal Test Comic",
   description = "Description.",
   imageUrl = "url.jpg",
   pageCount = 30,
   creators = listOf("Creator A")
  )
  val comic2 = Comic(
   id = "comic_id_003",
   title = "Equal Test Comic",
   description = "Description.",
   imageUrl = "url.jpg",
   pageCount = 30,
   creators = listOf("Creator A")
  )

  assertThat(comic1).isEqualTo(comic2)
  assertThat(comic1.hashCode()).isEqualTo(comic2.hashCode())
 }

 @Test
 fun `Comic instances with different IDs are not equal`() {

  val comic1 = Comic(
   id = "diff_id_1",
   title = "Title",
   description = "Desc",
   imageUrl = "url.jpg",
   pageCount = 10,
   creators = emptyList())

  val comic2 = Comic(
   id = "diff_id_2",
   title = "Title",
   description = "Desc",
   imageUrl = "url.jpg",
   pageCount = 10,
   creators = emptyList())

  assertThat(comic1).isNotEqualTo(comic2)
 }

 @Test
 fun `Comic copy function works correctly`() {
  val originalComic = Comic(
   id = "original_comic",
   title = "Original Title",
   description = "Original Description",
   imageUrl = "original.jpg",
   pageCount = 50,
   creators = listOf("John Doe")
  )

  val copiedComic = originalComic.copy(title = "Copied Title", pageCount = 55)

  assertThat(copiedComic.title).isEqualTo("Copied Title")
  assertThat(copiedComic.pageCount).isEqualTo(55)

  assertThat(copiedComic.id).isEqualTo("original_comic")
  assertThat(copiedComic.description).isEqualTo("Original Description")
  assertThat(copiedComic.imageUrl).isEqualTo("original.jpg")
  assertThat(copiedComic.creators).containsExactly("John Doe")

  assertThat(copiedComic).isNotEqualTo(originalComic)
 }

 @Test
 fun `Comic toString includes relevant fields`() {
  val comic = Comic(
   id = "comic_id_004",
   title = "ToString Comic",
   description = "This is for toString test.",
   imageUrl = "to_string.jpg",
   pageCount = 1,
   creators = listOf("Tester")
  )
  val expectedToStringPart = "Comic(id=comic_id_004, title=ToString Comic"

  assertThat(comic.toString()).contains(expectedToStringPart)
  assertThat(comic.toString()).contains("description=This is for toString test.")
  assertThat(comic.toString()).contains("pageCount=1")
  assertThat(comic.toString()).contains("creators=[Tester]")
 }
}