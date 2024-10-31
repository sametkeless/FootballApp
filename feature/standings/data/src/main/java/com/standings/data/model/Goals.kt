package com.standings.data.model

import com.google.gson.annotations.SerializedName

data class Goals(
    val against: Int,
    @SerializedName("for") val who: Int
)