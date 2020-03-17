package com.example.minisportapp

import com.google.gson.Gson
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Test


class SportAppDataTest {
    /**
     * This test asserts that the correctly structured json data is returned
     * when this function is called
     */
    @Test
    fun sportAppData_ParseSportData_ReturnsStructuredData() {
        val testData = "{\n" +
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

        val actual = Parser().parseSportData(Gson(), testData)
        val expected = SportData(
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

        Assert.assertThat(actual, `is`(expected))
    }
}