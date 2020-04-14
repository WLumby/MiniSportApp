package com.example.minisportapp.mvasterisk.mvc

import com.example.minisportapp.repository.SportDataRepository

interface TopicItemListMVCView {
    fun displayUpdatedData()
}

class TopicItemListController(private val view: TopicItemListMVCView, private val repository: SportDataRepository) {
    fun onViewReady() {
        // update the model
        // e.g. repository.setUrl("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")

        view.displayUpdatedData()
    }
}