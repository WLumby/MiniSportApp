package com.example.minisportapp.repository.networkData

import com.example.minisportapp.repository.*
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient

class HttpSportDataSource(
    private val wrapper: HTTPWrapper,
    private val httpClient: OkHttpClient
): SportDataSource {
    override fun fetchSportData(url: String) : Observable<String> {
        return Observable.create {emitter: ObservableEmitter<String> ->
            // TODO emitter.setCancellable
            try {
                emitter.onNext(wrapper.getRawData(httpClient, url))
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io())
    }
}

object SportDataRepositoryFactory {
    fun create() = SportDataRepository(
        HttpSportDataSource(HTTPWrapper(), OkHttpClient()),
        Parser(Gson())
    )
}
