package com.example.base.utils

sealed class UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>()
    data class Failure<T>(val message: String?) : UIState<T>()
    data object Loading: UIState<Nothing>()
}
