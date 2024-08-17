package com.example.weatherapp.common

sealed class Result(val error: Throwable? = null) {
    class Success : Result()
    class Failure(error: Throwable) : Result(error)
}