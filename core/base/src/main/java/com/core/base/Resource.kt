package com.core.base

sealed class Resource<out T : BaseAppModel> {

    data class Success<out T : BaseAppModel>(val data: T) : Resource<T>()

    data class Error(val error: BaseError) : Resource<Nothing>()

}