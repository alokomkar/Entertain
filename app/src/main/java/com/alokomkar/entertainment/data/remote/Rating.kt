package com.alokomkar.entertainment.data.remote

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("Source")
    var source: String = "",
    @SerializedName("Value")
    var value: String = ""
)