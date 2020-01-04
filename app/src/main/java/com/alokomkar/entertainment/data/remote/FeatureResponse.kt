package com.alokomkar.entertainment.data.remote

import com.alokomkar.entertainment.data.base.BaseResponse
import com.google.gson.annotations.SerializedName


data class FeatureResponse(
    @SerializedName("Search")
    var search: List<Feature> = listOf(),
    @SerializedName("totalResults")
    var totalResults: String = ""
) : BaseResponse()
