package com.example.minisportapp

import okhttp3.*

class Stats {
    fun sendStats(httpClient: OkHttpClient, command: String, parameterName: String, parameterValue: String): Response {
        val formBody: RequestBody = FormBody.Builder()
            .add("event", command)
            .add(parameterName, parameterValue)
            .build()

        val request: Request = Request.Builder()
            .method("POST", formBody)
            .url("https://bbc.github.io/sport-app-dev-tech-challenge/stats")
            .build()

        return httpClient.newCall(request).execute()
    }
}
