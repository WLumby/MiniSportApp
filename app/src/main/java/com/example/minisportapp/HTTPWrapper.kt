package com.example.minisportapp

import okhttp3.OkHttpClient
import okhttp3.Request

class HTTPWrapper() {
    /**
     * Function to get data from the given URL and return it
     * Takes a http client parameter to allow mocking
     */
    fun getRawData(client: OkHttpClient, url: String): String {
        // Request from url
        val request = Request.Builder().url(url).build()

        // Execute request and get response body to return
        val response = client.newCall(request).execute()
        return response.body!!.string()
    }
}

