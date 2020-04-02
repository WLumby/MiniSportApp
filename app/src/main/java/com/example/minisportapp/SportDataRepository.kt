package com.example.minisportapp

import android.os.AsyncTask
import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.net.URL

interface OnSPortDataResultListener {
    fun onResult(sportData: SportData)
}

class SportDataRepository(private val wrapper: HTTPWrapper, private val httpClient: OkHttpClient, private val parser: Parser, private val gson: Gson) {

    var listener: OnSPortDataResultListener? = null

    /**
     * Function to get and parse sport data from the data endpoint
     */
    fun getAndParseSportData() {
        val task = DownloadFilesTask(wrapper, httpClient)
        task.execute("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
    }

    fun onResult(result: String?) {
        result?.let {
            listener?.onResult(parser.parseSportData(
                gson,
                it
            ))
        }
    }

    inner class DownloadFilesTask(private val wrapper: HTTPWrapper, private val httpClient: OkHttpClient) : AsyncTask<String, Int, String?>() {
        override fun doInBackground(vararg url: String): String? {
            return try {
                wrapper.getRawData(
                    httpClient,
                    url.get(0)
                )
            } catch (e: Exception) {
                null
            }
        }

        override fun onPostExecute(result: String?) {
            onResult(result)
        }
    }
}