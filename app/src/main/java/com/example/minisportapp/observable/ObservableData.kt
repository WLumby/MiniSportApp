package com.example.minisportapp.observable

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.minisportapp.repository.SportData

interface SportObserver<T> {
    fun onValueChanged(value: T)
}

class ObservableData<T> {

    private val liveData = MutableLiveData<T>()

    private var liveDataObserver: Observer<T>? = null

    fun observe(observer: SportObserver<T>) {
        val newObserver = Observer<T> { t -> observer.onValueChanged(t) }
        liveData.observeForever(newObserver)
        liveDataObserver = newObserver
    }

    fun unsubscribe() {
        liveDataObserver?.also {
            liveData.removeObserver(it)
        }
    }

    var value: T get() {
        @Suppress("UNCHECKED_CAST")
        return liveData.value as T
    }

    set(value) {
        //Note: I chose postValue instead of setValue
        liveData.postValue(value)
    }
}