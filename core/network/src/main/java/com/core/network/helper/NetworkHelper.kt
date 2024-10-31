package com.core.network.helper

import com.core.base.BaseAppModel
import com.core.base.Resource
import com.core.network.R
import com.core.network.base.BaseNetworkDTOResponseModel
import com.core.network.error.NetworkError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <I : BaseAppModel, T : BaseNetworkDTOResponseModel<I>> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend () -> Response<T>
): Flow<Resource<I>> = withContext(dispatcher) {
    flow {
        val response = call.invoke()
        if (response.isSuccessful && response.body() != null && response.body()!!.errors.isEmpty()) {
            emit(Resource.Success(response.body()!!.map()))
        } else {
            emit(Resource.Error(NetworkError(R.string.err_empty)))
        }
    }.catch { exception ->
        when (exception) {
            is UnknownHostException, is SocketTimeoutException -> {
                emit(Resource.Error(NetworkError(R.string.err_connection)))
            }

            else -> {
                emit(Resource.Error(NetworkError()))
            }
        }
    }
}