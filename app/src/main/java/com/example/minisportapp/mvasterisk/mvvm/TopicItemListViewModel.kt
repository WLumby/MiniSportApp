package com.example.minisportapp.mvasterisk.mvvm

import com.example.minisportapp.repository.Item
import com.example.minisportapp.repository.OnSportDataResultListener
import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.SportDataRepository

sealed class TopicListItemData
data class TopicListItems(val items: List<Item>): TopicListItemData()
data class TopicListError(val message: String): TopicListItemData()

class TopicItemListViewModel(private val repository: SportDataRepository) : OnSportDataResultListener {
    interface Observer {
        fun onDataChanged(data: TopicListItemData)
    }

    var observer: Observer? = null

    init {
        repository.listener = this
    }

    fun onViewReady() {
        repository.provideSportData("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
    }

    override fun onResult(sportData: SportData) {
        observer?.onDataChanged(TopicListItems(sportData.data.items.asList()))
    }

    override fun onError() {
        observer?.onDataChanged(TopicListError("No data available"))
    }
}