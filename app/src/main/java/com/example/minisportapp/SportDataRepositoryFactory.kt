package com.example.minisportapp

import com.example.minisportapp.tasks.SportTaskFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient

object SportDataRepositoryFactory {
    fun create() : SportDataRepository {
        val wrapper = HTTPWrapper()
        val parser = Parser()
        val factory = SportTaskFactory()

        return SportDataRepository(wrapper, OkHttpClient(), parser, Gson(), factory)
    }
}