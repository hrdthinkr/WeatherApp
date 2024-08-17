package com.example.weatherapp.common

sealed class ResultResource(val message: String? = null) {
    class Success : ResultResource()
    class Failure(message: String) : ResultResource(message)
    class Loading : ResultResource()
}