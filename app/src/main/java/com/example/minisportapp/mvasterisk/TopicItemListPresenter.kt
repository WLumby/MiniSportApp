package com.example.minisportapp.mvasterisk

import com.example.minisportapp.repository.Item
import com.example.minisportapp.repository.OnSportDataResultListener
import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.SportDataRepository

interface TopicItemListView {
    fun displayTopicItems(items: List<Item>)
    fun displayError(message: String)
}

class TopicItemListPresenter(private val repository: SportDataRepository) : OnSportDataResultListener {
    init {
        repository.listener = this
    }
    var view: TopicItemListView? = null

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