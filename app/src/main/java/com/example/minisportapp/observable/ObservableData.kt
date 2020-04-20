package com.example.minisportapp.observable

import kotlin.properties.Delegates

interface Observer<T> {
    fun onValueChanged(value: T)
}

class ObservableData<T>(initValue: T, var observer: Observer<T>? = null) {
    var value: T by Delegates.observable(initValue) { _, _, newValue ->
        observer?.onValueChanged(newValue)
    }
}