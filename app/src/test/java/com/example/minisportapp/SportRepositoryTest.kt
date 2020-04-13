package com.example.minisportapp

import com.example.minisportapp.repository.*
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class SportRepositoryTest {
    private lateinit var mockOnSportDataResultListener: OnSportDataResultListener
    private lateinit var mockLocalDataSource: SportDataSource
    private lateinit var mockRemoteDataSource: SportDataSource
    private lateinit var sportDataRepository: SportDataRepository
    private val testUrl = "https://bbc.github.io/sport-app-dev-tech-challenge/data.json"
    private var localData: SportData? = null
    private var remoteData: SportData? = null

    @Before
    fun setup() {
        mockLocalDataSource = mock()
        mockRemoteDataSource = mock()
        mockOnSportDataResultListener = mock()
    }

    @Test
    fun `local data is provided when available`() {
        `given the repository is created`()
        `and local data is available`(testData)
        `and a listener is attached`()

        `when the repository is asked to provide data`(testUrl)

        `then the data is fetched from the local source`(testUrl)
        `and no data is fetched from the remote data source`()
        `and the correct data is provided to the listener`(testData)
    }

    @Test
    fun `remote data is provided when local is not available`() {
        `given the repository is created`()
        `and local data is not available`()
        `and remote data is available`(testData)
        `and a listener is attached`()

        `when the repository is asked to provide data`(testUrl)

        `then the data is fetched from the local source`(testUrl)
        `and the data is fetched from the remote source`(testUrl)
        `and the correct data is provided to the listener`(testData)
    }

    @Test
    fun `an error is provided when data is not available`() {
        `given the repository is created`()
        `and local data is not available`()
        `and remote data is not available`()
        `and a listener is attached`()

        `when the repository is asked to provide data`(testUrl)

        `then the data is fetched from the local source`(testUrl)
        `and the data is fetched from the remote source`(testUrl)
        `and an error is provided to the listener`()
    }

    private fun `given the repository is created`() {
        sportDataRepository = SportDataRepository(mockLocalDataSource, mockRemoteDataSource)
    }

    private fun `and local data is available`(data: SportData) {
        localData = data
    }

    private fun `and local data is not available`() {
        localData = null
    }

    private fun `and remote data is available`(data: SportData) {
        remoteData = data
    }

    private fun `and remote data is not available`() {
        remoteData = null
    }

    private fun `and a listener is attached`() {
        sportDataRepository.listener = mockOnSportDataResultListener
    }

    private fun `when the repository is asked to provide data`(url: String) {
        sportDataRepository.provideSportData(url)
    }

    private fun `then the data is fetched from the local source`(url: String) {
        val localCallbackCaptor = argumentCaptor<(SportData?) -> Unit>()
        verify(mockLocalDataSource).fetchSportData(eq(url), localCallbackCaptor.capture())
        localCallbackCaptor.firstValue.invoke(localData)
    }

    private fun `and the data is fetched from the remote source`(url: String) {
        val remoteCallbackCaptor = argumentCaptor<(SportData?) -> Unit>()
        verify(mockRemoteDataSource).fetchSportData(eq(url), remoteCallbackCaptor.capture())
        remoteCallbackCaptor.firstValue.invoke(remoteData)
    }

    private fun `and the correct data is provided to the listener`(expectedData: SportData) {
        verify(mockOnSportDataResultListener).onResult(expectedData)
        verify(mockOnSportDataResultListener, never()).onError()
    }

    private fun `and an error is provided to the listener`() {
        verify(mockOnSportDataResultListener, never()).onResult(any())
        verify(mockOnSportDataResultListener).onError()
    }

    private fun `and no data is fetched from the remote data source`() {
        verify(mockRemoteDataSource, never()).fetchSportData(any(), any())
    }

    private val testData = SportData(
        Data(
            Topic(
                "Formula 1",
                "https://www.bbc.co.uk/sport/formula1"
            ),
            Array(1) {
                Item(
                    "story",
                    "Schumacher junior wins European F3 title with race to spare",
                    "https://www.bbc.co.uk/sport/motorsport/45851176",
                    "Formula 1",
                    "https://www.bbc.co.uk/sport/formula1",
                    false,
                    "18h",
                    null,
                    null,
                    1539529691,
                    Image(
                        "https://ichef.bbci.co.uk/onesport/cps/320/cpsprodpb/16477/production/_103855219_schumacher.jpg",
                        "https://ichef.bbci.co.uk/onesport/cps/512/cpsprodpb/16477/production/_103855219_schumacher.jpg",
                        "https://ichef.bbci.co.uk/onesport/cps/976/cpsprodpb/16477/production/_103855219_schumacher.jpg",
                        "Mick Schumacher",
                        "Getty Images"
                    )
                )
            }
        )
    )
}