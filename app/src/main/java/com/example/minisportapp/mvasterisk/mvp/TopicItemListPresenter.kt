package com.example.minisportapp.mvasterisk.mvp

import com.example.minisportapp.repository.Item
import com.example.minisportapp.repository.OnSportDataResultListener
import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.SportDataRepository

interface TopicItemListMVPView {
    fun displayTopicItems(items: List<Item>)
    fun displayError(message: String)
}

class TopicItemListPresenter(private val repository: SportDataRepository) : OnSportDataResultListener {
    init {
        repository.listener = this
    }
    var view: TopicItemListMVPView? = null

    fun onViewReady() {
        repository.provideSportData("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
    }

    override fun onResult(sportData: SportData) {
        view?.displayTopicItems(sportData.data.items.asList())
    }

    override fun onError() {
        view?.displayError("No data available")
    }
}