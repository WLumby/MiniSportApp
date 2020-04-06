package com.example.minisportapp

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Test

class SportDataTest {
    @Test fun dataIsDisplayed() {
        // Given: data is available

        val mockFactory: DownloadFilesTaskFactory = mock()
        val mockDownloadFilesTask: DownloadFilesTask = mock()
        whenever(mockFactory.createDownloadFilesTask(any(), any(), any())).thenReturn(mockDownloadFilesTask)

        val sportDataRepository = SportDataRepository(mock(), mock(), mock(), mock(), mockFactory)
        val mockOnSportDataResultListener: OnSportDataResultListener = mock()
        sportDataRepository.listener = mockOnSportDataResultListener

        // When: view is created
        val url = "https://bbc.github.io/sport-app-dev-tech-challenge/data.json"
        sportDataRepository.getAndParseSportData(url)
        verify(mockDownloadFilesTask).execute(url)

        // Then: data should be presented to view
        verify(mockOnSportDataResultListener).onResult(any())
    }
}