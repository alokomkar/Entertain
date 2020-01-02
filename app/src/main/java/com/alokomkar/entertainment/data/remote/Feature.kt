package com.alokomkar.entertainment.data.remote

import com.alokomkar.entertainment.data.base.BaseResponse
import com.alokomkar.entertainment.data.local.FeatureLocal
import com.google.gson.annotations.SerializedName

data class Feature(
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
) : BaseResponse() {
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