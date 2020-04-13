package com.example.minisportapp.repository

import android.os.AsyncTask
import com.google.gson.Gson
import okhttp3.OkHttpClient
import kotlin.random.Random

class RemoteSportDataSource(private val wrapper: HTTPWrapper, private val httpClient: OkHttpClient, private val parser: Parser):
    SportDataSource {
    override fun fetchSportData(url: String, onResult: (SportData?) -> Unit) {
        if (Random.Default.nextBoolean()) {
            onResult(null)
            return
        }

        DownloadDataAsyncTask(
            wrapper,
            httpClient
        ) { json ->
            json?.let {
                onResult(parser.parseSportData(json))
            }
        }.execute(url)
    }
}

class DownloadDataAsyncTask(private val wrapper: HTTPWrapper, private val httpClient: OkHttpClient, private val onResult: (String?) -> Unit) : AsyncTask<String, Int, String?>() {
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
