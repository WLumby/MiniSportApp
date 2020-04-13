package com.example.minisportapp.repository

import com.google.gson.Gson
import okhttp3.OkHttpClient

interface OnSportDataResultListener {
    fun onResult(sportData: SportData)
    fun onError()
}

interface SportDataSource {
    fun fetchSportData(url: String, onResult: (SportData?) -> Unit)
}

object SportDataRepositoryFactory {
    fun create() = SportDataRepository(
        LocalSportDataSource(),
        RemoteSportDataSource(HTTPWrapper(), OkHttpClient(), Parser(Gson()))
    )
}

class SportDataRepository(private val localDataSource: SportDataSource, private val remoteDataSource: SportDataSource) {
    var listener: OnSportDataResultListener? = null

    fun provideSportData(url: String) {
        localDataSource.fetchSportData(url) {
            onResult(it, remoteDataSource, url)
        }
    }

    private fun onResult(result: SportData?, fallbackDataSource: SportDataSource?, url: String) {
        when(result) {
            null -> {
                when(fallbackDataSource) {
                    null -> listener?.onError()
                    else -> fallbackDataSource.fetchSportData(url) {onResult(it, null, url)}
                }
            }
            else -> listener?.onResult(result)
        }
    }
}
