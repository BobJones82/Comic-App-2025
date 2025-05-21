package com.pas.comicapp.navigation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ScreenTest {

 @Test
 fun `ComicList screen route is correct`() {
  val comicListScreen = Screen.ComicList
  assertThat(comicListScreen.route).isEqualTo("comic_list")
 }

 @Test
 fun `ComicDetails createRoute generates correct path with valid ID`() {
  val comicId = "marvel_spiderman_001"
  val expectedRoute = "comic_details/marvel_spiderman_001"
  val actualRoute = Screen.ComicDetails.createRoute(comicId)

  assertThat(actualRoute).isEqualTo(expectedRoute)
 }

 @Test
 fun `ComicDetails createRoute handles special characters in ID correctly`() {
  val comicId = "dc_batman-v-superman_#1"
  val expectedRoute = "comic_details/dc_batman-v-superman_#1"
  val actualRoute = Screen.ComicDetails.createRoute(comicId)

  assertThat(actualRoute).isEqualTo(expectedRoute)
 }

 @Test
 fun `ComicDetails createRoute handles empty ID gracefully`() {
  val comicId = ""
  val expectedRoute = "comic_details/"
  val actualRoute = Screen.ComicDetails.createRoute(comicId)

  assertThat(actualRoute).isEqualTo(expectedRoute)
 }
}