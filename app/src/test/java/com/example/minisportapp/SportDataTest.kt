package com.example.minisportapp

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Test

class SportDataTest {
    @Test fun dataIsDisplayed() {
        // Given: data is available

        val sportDataRepository = SportDataRepository(mock(), mock(), mock(), mock())
        val mockOnSportDataResultListener: OnSPortDataResultListener = mock()
        sportDataRepository.listener = mockOnSportDataResultListener

        // When: view is created
        sportDataRepository.getAndParseSportData()

        // Then: data should be presented to view
        verify(mockOnSportDataResultListener).onResult(any())
    }
}