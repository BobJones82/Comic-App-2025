package com.pas.comicapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.pas.comicapp.data.api.ComicApiService
import com.pas.comicapp.data.model.ComicDto
import com.pas.comicapp.domain.model.Comic
import com.pas.comicapp.domain.model.toDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ComicRepositoryTest {

 private lateinit var mockApiService: ComicApiService
 private lateinit var comicRepository: ComicRepository

 @Before
 fun setup() {
  mockApiService = mockk()
  comicRepository = ComicRepository(mockApiService)
 }


 @Test
 fun `getComics returns mapped domain models on API success`() = runTest {
  val apiResponseDtos = listOf(
   ComicDto(
    "id1",
    "Title 1",
    "Desc 1",
    "img1.jpg",
    32,
    listOf("Artist A")),

   ComicDto("id2",
    "Title 2",
    "Desc 2",
    "img2.jpg",
    40,
    listOf("Writer B", "Artist C"))
  )
  val expectedDomainComics = apiResponseDtos.map { it.toDomain() }

  coEvery { mockApiService.getComics() } returns apiResponseDtos

  val result = comicRepository.getComics()

  assertThat(result).isEqualTo(expectedDomainComics)
 }

 @Test
 fun `getComics returns empty list when API returns empty list`() = runTest {
  coEvery { mockApiService.getComics() } returns emptyList()

  val result = comicRepository.getComics()

  assertThat(result).isEmpty()
 }

 @Test(expected = IOException::class)
 fun `getComics rethrows IOException on API network error`() = runTest {
  coEvery { mockApiService.getComics() } throws IOException("Network error")

  comicRepository.getComics()

 }

 @Test(expected = Exception::class)
 fun `getComics rethrows generic Exception on API error`() = runTest {
  coEvery { mockApiService.getComics() } throws Exception("Something went wrong on API")

  comicRepository.getComics()
 }

 @Test
 fun `getComicDetails returns correct comic on success`() = runTest {
  val targetComicId = "id_to_find"
  val targetComicDto = ComicDto(
   targetComicId,
   "Target Title",
   "Target Desc",
   "target.jpg",
   50,
   listOf("Creator Z"))

  val apiResponseDtos = listOf(
   ComicDto(
    "other_id1",
    "Other Title 1",
    "Other Desc 1",
    "other1.jpg",
    30,
    emptyList()),
   targetComicDto,
   ComicDto(
    "other_id2",
    "Other Title 2",
    "Other Desc 2",
    "other2.jpg",
    40,
    emptyList())
  )
  val expectedDomainComic = targetComicDto.toDomain()

  coEvery { mockApiService.getComics() } returns apiResponseDtos

  val result = comicRepository.getComicDetails(targetComicId)

  assertThat(result).isEqualTo(expectedDomainComic)
 }

 @Test(expected = NoSuchElementException::class)
 fun `getComicDetails throws NoSuchElementException if comic not found`() = runTest {
  val apiResponseDtos = listOf(
   ComicDto(
    "id_other_1",
    "Other Title 1",
    "Other Desc 1",
    "other1.jpg",
    30,
    emptyList()),
   ComicDto(
    "id_other_2",
    "Other Title 2",
    "Other Desc 2",
    "other2.jpg",
    40,
    emptyList())
  )
  coEvery { mockApiService.getComics() } returns apiResponseDtos

  comicRepository.getComicDetails("non_existent_id")

 }

 @Test(expected = IOException::class)
 fun `getComicDetails rethrows IOException on API network error during initial fetch`() = runTest {
  coEvery { mockApiService.getComics() } throws IOException("Network error fetching details")

  comicRepository.getComicDetails("any_id")

 }

 @Test(expected = Exception::class)
 fun `getComicDetails rethrows generic Exception on API error during initial fetch`() = runTest {
  coEvery { mockApiService.getComics() } throws Exception("API error fetching details")

  comicRepository.getComicDetails("any_id")

 }

 private fun createComicDto(id: String, title: String): ComicDto {
  return ComicDto(
   id,
   title,
   "description",
   "imageUrl",
   22,
   listOf("Creator A"))
 }

 private fun createComic(id: String, title: String): Comic {
  return Comic(
   id,
   title,
   "description",
   "imageUrl",
   22,
   listOf("Creator A"))
 }
}