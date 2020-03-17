package com.example.minisportapp

import com.google.gson.Gson
import okhttp3.OkHttpClient

class SportDataRepository {
    /**
     * Function to get and parse sport data from the data endpoint
     */
    fun getAndParseSportData(wrapper: HTTPWrapper, httpClient: OkHttpClient, parser: Parser, gson: Gson): SportData? {
        val rawData: String

        try {
            rawData = wrapper.getRawData(
                httpClient,
                "https://bbc.github.io/sport-app-dev-tech-challenge/data.json"
            )
        } catch (e: Exception) {
            return null
        }

        return parser.parseSportData(
            gson,
            rawData
        )
    }
}