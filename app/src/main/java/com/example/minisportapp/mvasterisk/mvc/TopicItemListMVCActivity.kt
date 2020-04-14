package com.example.minisportapp.mvasterisk.mvc

import android.os.Bundle
import android.widget.Toast
import com.example.minisportapp.mvasterisk.BaseMVAsteriskActivity
import com.example.minisportapp.repository.OnSportDataResultListener
import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.SportDataRepository
import com.example.minisportapp.repository.SportDataRepositoryFactory

class TopicItemListMVCActivity : BaseMVAsteriskActivity(), TopicItemListMVCView, OnSportDataResultListener {

    private lateinit var controller: TopicItemListController
    private lateinit var repository: SportDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = SportDataRepositoryFactory.create()
        repository.listener = this
        controller = TopicItemListController(this, repository)
    }

    override fun onResume() {
        super.onResume()
        controller.onViewReady()
    }

    override fun onResult(sportData: SportData) {
        updateListItems(sportData.data.items.asList())
    }

    override fun onError() {
        Toast.makeText(this, "No data available", Toast.LENGTH_LONG).show()
    }

    override fun displayUpdatedData() {
        repository.provideSportData("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
    }
}
