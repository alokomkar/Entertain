package com.alokomkar.entertainment.data.base

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("Response")
    var code : String? = ""
    @SerializedName("Error")
    var message: String? = ""
}