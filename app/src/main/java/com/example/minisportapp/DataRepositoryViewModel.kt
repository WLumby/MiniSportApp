package com.example.minisportapp

import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.networkData.SportDataRepositoryFactory
import io.reactivex.rxjava3.core.Observable

class DataRepositoryViewModel {
    val data: Observable<SportData> = SportDataRepositoryFactory.create()
        .getAndParseSportData("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
}