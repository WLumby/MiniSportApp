package com.example.minisportapp

import com.example.minisportapp.tasks.GenericSportTask
import com.example.minisportapp.tasks.SportTaskFactory
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Test

class SportDataTest {
    @Test fun dataIsDisplayed() {
        // Given: data is available

        val mockFactory: SportTaskFactory = mock()
        val mockDownloadFilesTask: GenericSportTask<String, String?> = mock()
        whenever(mockFactory.createTask<String, String?>(any(), any())).thenReturn(mockDownloadFilesTask)

        val sportDataRepository = SportDataRepository(mock(), mock(), mock(), mock(), mockFactory)
        val mockOnSportDataResultListener: OnSportDataResultListener = mock()
        sportDataRepository.listener = mockOnSportDataResultListener

        // When: view is created
        val url = "https://bbc.github.io/sport-app-dev-tech-challenge/data.json"
        sportDataRepository.getAndParseSportData(url)
        verify(mockDownloadFilesTask).executeTask(url)
        val captor = argumentCaptor<(String?) -> Unit>()
        verify(mockFactory).createTask<String, String?>(any(), captor.capture())
        captor.firstValue.invoke("blah")

        val expectedData: SportData = mock()

        // Then: data should be presented to view
        verify(mockOnSportDataResultListener).onResult(expectedData)
    }
}