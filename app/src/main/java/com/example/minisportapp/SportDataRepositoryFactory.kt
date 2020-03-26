package com.example.minisportapp

import com.google.gson.Gson
import okhttp3.OkHttpClient

object SportDataRepositoryFactory {
    fun create() : SportDataRepository {
        val wrapper = HTTPWrapper()
        val parser = Parser()
        return SportDataRepository(wrapper, OkHttpClient(), parser, Gson())
    }
}