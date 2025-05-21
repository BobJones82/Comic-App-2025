package com.pas.comicapp.data.api

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.pas.comicapp.data.model.ComicDto
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ComicApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var comicApiService: ComicApiService
    private lateinit var gson: Gson

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        gson = Gson()

        comicApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ComicApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getComics returns correct list on success`() = runTest {
        val expectedComics = listOf(
            ComicDto(
                id = "comic_test_001",
                title = "Test Comic 1",
                description = "Description for test comic 1.",
                imageUrl = "http://example.com/image1.jpg",
                pageCount = 30,
                creators = listOf("Writer A", "Artist B")
            ),
            ComicDto(
                id = "comic_test_002",
                title = "Test Comic 2",
                description = "Description for test comic 2.",
                imageUrl = "http://example.com/image2.jpg",
                pageCount = 45,
                creators = listOf("Writer C")
            )
        )
        val jsonBody = gson.toJson(expectedComics)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(jsonBody)
        )

        val actualComics = comicApiService.getComics()

        assertThat(actualComics).isEqualTo(expectedComics)

        val recordedRequest = mockWebServer.takeRequest()
        assertThat(recordedRequest.path).isEqualTo(
            "/v3/f40078b6-78be-498c-b919-e6586f3be0c0")
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `getComics throws HttpException on server error`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .setBody("Server Error")
        )

        comicApiService.getComics()
    }

    @Test(expected = java.io.IOException::class)
    fun `getComics throws IOException on network failure`() = runTest {
        mockWebServer.shutdown()

        comicApiService.getComics()
    }
}