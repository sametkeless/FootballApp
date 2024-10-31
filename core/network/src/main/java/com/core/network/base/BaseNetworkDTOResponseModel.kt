package com.core.network.base

import com.core.base.BaseAppModel
import com.google.gson.annotations.SerializedName

abstract class BaseNetworkDTOResponseModel<I : BaseAppModel> {
    @SerializedName("errors")
    val errors: List<Errors?> = emptyList()

        abstract suspend fun map(): I
}