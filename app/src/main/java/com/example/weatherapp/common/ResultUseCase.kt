package com.example.weatherapp.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

abstract class ResultUseCase {
    protected fun execute(
        block: suspend () -> Result
    ): Flow<ResultResource> = flow {
        try {
            emit(ResultResource.Loading())
            val result = block.invoke()

            when (result) {
                is Result.Success -> {
                    emit(ResultResource.Success())
                }
                is Result.Failure -> {
                    val error = result.error
                    emit(ResultResource.Failure("Operation failed with error: $error."))
                }
            }
        } catch (e: HttpException) {
            emit(ResultResource.Failure(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(ResultResource.Failure("Couldn't reach the server. Check your internet connection."))
        }
    }
}
