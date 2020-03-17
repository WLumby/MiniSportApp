package com.example.minisportapp

import com.google.gson.Gson

class Parser {

    /**
     * Function to unmarshal raw string data into SportData structure and return it
     */
    fun parseSportData(gson: Gson, data: String): SportData {
        return gson.fromJson(data, SportData::class.java)
    }
}