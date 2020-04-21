package com.example.minisportapp

import com.example.minisportapp.observable.ObservableData
import com.example.minisportapp.repository.OnSportDataResultListener
import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.networkData.SportDataRepositoryFactory

class DataRepositoryViewModel : OnSportDataResultListener {

    val data: ObservableData<SportData?> = ObservableData(null)

    fun attachToRepository() {
        //todo: get rid of this lad
        val sportDataRepository = SportDataRepositoryFactory.create()
        sportDataRepository.listener = this
        sportDataRepository.getAndParseSportData("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
    }

    override fun onResult(sportData: SportData) {
        data.value = sportData
    }


}