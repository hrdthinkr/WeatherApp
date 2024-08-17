package com.example.weatherapp.domain.usecase

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.weatherapp.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

abstract class UseCase<T> {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    protected fun execute(
        block: suspend () -> T
    ): Flow<Resource<T>> = flow {
        try {
            emit(Resource.Loading())
            val result = block.invoke()

            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}