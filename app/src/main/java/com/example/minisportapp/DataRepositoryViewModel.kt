package com.example.minisportapp

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.minisportapp.observable.ObservableData
import com.example.minisportapp.repository.OnSportDataResultListener
import com.example.minisportapp.repository.SportData
import com.example.minisportapp.repository.networkData.SportDataRepositoryFactory

class DataRepositoryViewModel : ViewModel(), OnSportDataResultListener {

    val data: ObservableData<SportData?> = ObservableData()

    val itemCount: ObservableData<Int> = ObservableData()

    init {
        itemCount.value = 0
    }

    fun attachToRepository() {
        //todo: get rid of this lad
        val sportDataRepository = SportDataRepositoryFactory.create()
        sportDataRepository.listener = this
        sportDataRepository.getAndParseSportData("https://bbc.github.io/sport-app-dev-tech-challenge/data.json")
    }

    override fun onResult(sportData: SportData) {
        data.value = sportData
        itemCount.value = sportData.data.items.size
    }

    override fun onCleared() {
        super.onCleared()
        data.unsubscribe()
        itemCount.unsubscribe()
    }
}