package com.example.minisportapp

import com.google.gson.Gson
import okhttp3.OkHttpClient

class SportDataRepository(private val wrapper: HTTPWrapper, private val httpClient: OkHttpClient, private val parser: Parser, private val gson: Gson) {
    /**
     * Function to get and parse sport data from the data endpoint
     */
    fun getAndParseSportData(): SportData? {
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