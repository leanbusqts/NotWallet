package dev.bulean.notwallet.model

import java.io.IOException
import retrofit2.HttpException

sealed interface Error {
    class Server(val code: Int) : Error
    object Connectivity : Error
    class Unknown(val message: String) : Error
}

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}