package com.example.minisportapp.repository

import kotlin.random.Random

class LocalSportDataSource: SportDataSource {
    private val localData = SportData(
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

    override fun fetchSportData(url: String, onResult: (SportData?) -> Unit) {
        onResult(if (Random.Default.nextBoolean()) localData else null)
    }
}