package com.example.minisportapp.repository

interface OnSportDataResultListener {
    fun onResult(sportData: SportData)
}

interface SportDataSource {
    fun fetchSportData(url: String, onResult: (String?) -> Unit)
}

class SportDataRepository(
    private val dataSource: SportDataSource,
    private val parser: Parser
) {
    var listener: OnSportDataResultListener? = null

    fun getAndParseSportData(url: String) {
        dataSource.fetchSportData(url, ::onResult)
    }

    private fun onResult(result: String?) {
        result?.let {
            listener?.onResult(parser.parseSportData(it))
        }
    }
}
