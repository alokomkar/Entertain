package com.alokomkar.entertainment.data.local

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Bookmark(
    @PrimaryKey
    @SerializedName("imdbID")
    var imdbID: String = "",
    @SerializedName("Poster")
    var poster: String = "",
    @SerializedName("Title")
    var title: String = "",
    @SerializedName("Type")
    var type: String = "",
    @SerializedName("Year")
    var year: String = ""
) {

    @Ignore
    var isBookmarked : Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeatureLocal

        if (imdbID != other.imdbID) return false

        return true
    }

    override fun hashCode(): Int {
        return imdbID.hashCode()
    }
}