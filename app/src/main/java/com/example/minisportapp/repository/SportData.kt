package com.example.minisportapp.repository

data class SportData(
    val data: Data
)

data class Data(
    val topic: Topic,
    val items: Array<Item>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (topic != other.topic) return false
        if (!items.contentEquals(other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = topic.hashCode()
        result = 31 * result + items.contentHashCode()
        return result
    }
}

data class Topic(
    val title: String,
    val url: String
)

data class Item(
    val type: String,
    val title: String,
    val url: String,
    val sectionLabel: String,
    val sectionUrl: String,
    val isLive: Boolean,
    val lastUpdatedText: String,
    val mediaType: String?,
    val contentType: String?,
    val lastUpdatedTimestamp: Int,
    val image: Image
)

data class Image(
    val small: String,
    val medium: String,
    val large: String,
    val altText: String,
    val copyrightHolder: String
)