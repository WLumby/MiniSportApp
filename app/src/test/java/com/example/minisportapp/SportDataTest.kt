package com.example.minisportapp

import com.nhaarman.mockitokotlin2.*
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
        val captor = argumentCaptor<(String?) -> Unit>()
        verify(mockFactory).createDownloadFilesTask(any(), any(), captor.capture())
        captor.firstValue.invoke("blah")

        // Then: data should be presented to view
        val expectedData: SportData = mock()
        verify(mockOnSportDataResultListener).onResult(expectedData)
    }
}