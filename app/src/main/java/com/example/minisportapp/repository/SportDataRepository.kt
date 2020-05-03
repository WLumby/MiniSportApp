package com.example.minisportapp.repository

import io.reactivex.rxjava3.core.Observable

interface SportDataSource {
    fun fetchSportData(url: String) : Observable<String>
}

class SportDataRepository(
    private val dataSource: SportDataSource,
    private val parser: Parser
) {
    fun getAndParseSportData(url: String): Observable<SportData> {
        return dataSource.fetchSportData(url)
            .map { parser.parseSportData(it) }
    }
}
