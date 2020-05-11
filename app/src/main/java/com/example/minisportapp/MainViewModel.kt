package com.example.minisportapp

import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.networkData.SportDataRepositoryFactory
import io.reactivex.rxjava3.core.Observable

sealed class MainViewDisplayData
data class Success(val data: SportData) : MainViewDisplayData()
data class Error(val message: String) : MainViewDisplayData()

class MainViewModel {
    val displayData: Observable<MainViewDisplayData> = SportDataRepositoryFactory.create()
        .getAndParseSportData("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
        .map { sportData: SportData -> Success(sportData) }
}