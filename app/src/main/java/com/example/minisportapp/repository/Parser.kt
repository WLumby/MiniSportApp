package com.example.minisportapp.repository

import com.google.gson.Gson

class Parser(private val gson: Gson) {
    fun parseSportData(data: String): SportData {
        return gson.fromJson(data, SportData::class.java)
    }
}