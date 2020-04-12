package com.example.minisportapp

import com.example.minisportapp.repository.*
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class SportRepositoryTest {
    private lateinit var mockOnSportDataResultListener: OnSportDataResultListener
    private lateinit var mockDataSource: SportDataSource
    private lateinit var sportDataRepository: SportDataRepository
    private val testUrl = "https://bbc.github.io/sport-app-dev-tech-challenge/data.json"

    @Before
    fun setup() {
        mockDataSource = mock()
        mockOnSportDataResultListener = mock()
    }

    @Test
    fun `sport data is presented to the view`() {
        `given the repository is created`()
        `and a listener is attached`()

        `when the repository is asked to provide data`(testUrl)

        `then the data is fetched from the correct url`(testUrl)
        `and the correct data is presented to the listener`()
    }

    private fun `given the repository is created`() {
        sportDataRepository = SportDataRepository(mockDataSource, Parser(Gson()))
    }

    private fun `and a listener is attached`() {
        sportDataRepository.listener = mockOnSportDataResultListener
    }

    private fun `when the repository is asked to provide data`(url: String) {
        sportDataRepository.getAndParseSportData(url)
    }

    private fun `then the data is fetched from the correct url`(url: String) {
        val captor = argumentCaptor<(String?) -> Unit>()
        verify(mockDataSource).fetchSportData(eq(url), captor.capture())
        captor.firstValue.invoke(testDataJson)
    }

    private fun `and the correct data is presented to the listener`() {
        verify(mockOnSportDataResultListener).onResult(testData)
    }

    val testDataJson = "{\n" +
            "  \"data\": {\n" +
            "    \"topic\": {\n" +
            "      \"title\": \"Formula 1\",\n" +
            "      \"url\": \"https://www.bbc.co.uk/sport/formula1\"\n" +
            "    },\n" +
            "    \"items\": [\n" +
            "      {\n" +
            "        \"type\": \"story\",\n" +
            "        \"title\": \"Schumacher junior wins European F3 title with race to spare\",\n" +
            "        \"url\": \"https://www.bbc.co.uk/sport/motorsport/45851176\",\n" +
            "        \"sectionLabel\": \"Formula 1\",\n" +
            "        \"sectionUrl\": \"https://www.bbc.co.uk/sport/formula1\",\n" +
            "        \"isLive\": false,\n" +
            "        \"lastUpdatedText\": \"18h\",\n" +
            "        \"mediaType\": null,\n" +
            "        \"contentType\": null,\n" +
            "        \"lastUpdatedTimestamp\": 1539529691,\n" +
            "        \"image\": {\n" +
            "          \"small\": \"https://ichef.bbci.co.uk/onesport/cps/320/cpsprodpb/16477/production/_103855219_schumacher.jpg\",\n" +
            "          \"medium\": \"https://ichef.bbci.co.uk/onesport/cps/512/cpsprodpb/16477/production/_103855219_schumacher.jpg\",\n" +
            "          \"large\": \"https://ichef.bbci.co.uk/onesport/cps/976/cpsprodpb/16477/production/_103855219_schumacher.jpg\",\n" +
            "          \"altText\": \"Mick Schumacher\",\n" +
            "          \"copyrightHolder\": \"Getty Images\"\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}"

    val testData = SportData(
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