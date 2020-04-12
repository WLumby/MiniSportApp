package com.example.minisportapp.repository.networkData

import android.os.AsyncTask
import com.example.minisportapp.repository.*
import com.google.gson.Gson
import okhttp3.OkHttpClient

class HttpSportDataSource(private val wrapper: HTTPWrapper, private val httpClient: OkHttpClient):
    SportDataSource {
    override fun fetchSportData(url: String, onResult: (String?) -> Unit) {
        DownloadDataAsyncTask(wrapper, httpClient, onResult).execute(url)
    }
}

object SportDataRepositoryFactory {
    fun create() = SportDataRepository(
        HttpSportDataSource(HTTPWrapper(), OkHttpClient()),
        Parser(Gson())
    )
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
