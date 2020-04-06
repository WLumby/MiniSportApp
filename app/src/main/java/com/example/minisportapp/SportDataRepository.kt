package com.example.minisportapp

import android.os.AsyncTask
import com.example.minisportapp.tasks.SportTaskFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient

interface OnSportDataResultListener {
    fun onResult(sportData: SportData)
}

//TODO: REMOVE ME
class DownloadFilesTask(private val wrapper: HTTPWrapper, private val httpClient: OkHttpClient, private val onResult: (String?) -> Unit) : AsyncTask<String, Int, String?>() {
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

class SportDataRepository(
    private val wrapper: HTTPWrapper,
    private val httpClient: OkHttpClient,
    private val parser: Parser,
    private val gson: Gson,
    private val taskFactory: SportTaskFactory) {

    var listener: OnSportDataResultListener? = null

    /**
     * Function to get and parse sport data from the data endpoint
     */
    fun getAndParseSportData(url: String) {
        val task = taskFactory.createTask(::fetchSportData, ::onResult)
        task.executeTask(url)
    }

    private fun fetchSportData(url: String): String? {
        //TODO: write me
        return "REPLACE THIS"
    }

    private fun onResult(result: String?) {
        result?.let {
            listener?.onResult(parser.parseSportData(
                gson,
                it
            ))
        }
    }
}