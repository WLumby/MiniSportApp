package com.example.minisportapp

import com.example.minisportapp.repository.networkData.HTTPWrapper
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HTTPWrapperTest {
    /**
     * Helper function allowing actual 'any' parameter tests without
     * getting IllegalStateException
     *
     * Why you have to do this is beyond me
     */
    private fun <T> any(): T = Mockito.any<T>()

    @Mock
    private lateinit var mockHTTPClient: OkHttpClient

    @Mock
    private lateinit var mockHTTPCall: Call

    /**
     * This test asserts that GetRawData works correctly with a
     * mock HTTP client
     */
    @Test
    fun httpWrapper_GetRawData_ReturnsRawData() {
        // Setup test values
        val testURL = "http://test.test.com"
        val testRequest = Request.Builder().url(testURL).build()
        val testResponseBody = "{\"hello\":\"hi\"}".toResponseBody()
        val testResponse = Response.Builder().
            body(testResponseBody).
            code(200).
            request(testRequest).
            protocol(Protocol.HTTP_1_0).
            message("200 OK").
            build()

        // Setup mock HTTP client and call
        `when`(mockHTTPClient.newCall(any()))
            .thenReturn(mockHTTPCall)

        `when`(mockHTTPCall.execute())
            .thenReturn(testResponse)

        val wrapper =
            HTTPWrapper()
        val rawData = wrapper.getRawData(mockHTTPClient, testURL)

        assertThat(rawData, `is`("{\"hello\":\"hi\"}"))
    }
}
