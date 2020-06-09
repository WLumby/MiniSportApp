package com.example.minisportapp.observable

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class ObservableDataTest {
    private lateinit var observer: SpyObserver<String?>
    private lateinit var observed: ObservableData<String?>

    @Before
    fun setup() {
        observer = SpyObserver()
    }

    @Test
    fun `observer is notified on change`() {
        observed = ObservableData()

        observed.value = "Hello World!"

        assertThat(observer.observedValue, `is`("Hello World!"))
    }

    class SpyObserver<T>() : SportObserver<T> {
        var observedValue: T? = null
        override fun onValueChanged(value: T) {
            observedValue = value
        }

    }
}