package com.pas.comicapp.di

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.pas.comicapp.data.api.ComicApiService
import com.pas.comicapp.data.model.ComicDto
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class AppModuleTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var comicApiService: ComicApiService
    private lateinit var gson: Gson

    private lateinit var appModule: AppModule

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        gson = Gson()
        appModule = AppModule

        val retrofitInstance = appModule.provideRetrofit().newBuilder()
            .baseUrl(mockWebServer.url("/"))
            .build()

        comicApiService = appModule.provideComicApiService(retrofitInstance)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `provideRetrofit provides Retrofit instance with correct base URL and converter`() {

        val expectedPath = "/v3/f40078b6-78be-498c-b919-e6586f3be0c0"

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(gson.toJson(emptyList<ComicDto>()))
        )

        runTest {
            comicApiService.getComics()

            val recordedRequest = mockWebServer.takeRequest()
            assertThat(recordedRequest.path).isEqualTo(expectedPath)
        }
    }

    @Test
    fun `provideComicApiService provides ComicApiService correctly configured`() = runTest {

        val expectedComic = ComicDto(
            id = "test_id_001",
            title = "Test Comic Title",
            description = "A brief description.",
            imageUrl = "http://test.image.com/test.jpg",
            pageCount = 24,
            creators = listOf("Creator One", "Creator Two")
        )
        val jsonBody = gson.toJson(listOf(expectedComic))

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(jsonBody)
        )

        val actualComics = comicApiService.getComics()

        assertThat(actualComics).containsExactly(expectedComic)
        assertThat(actualComics.first().id).isEqualTo("test_id_001")
        assertThat(actualComics.first().title).isEqualTo("Test Comic Title")
    }
}