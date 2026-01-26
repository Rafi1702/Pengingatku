package com.example.pengingatku.utils

sealed class StateHelper<out T>{
    object Loading: StateHelper<Nothing>()
    class Success<T>(val data:T ) : StateHelper<T>()
    class Failure(val exception: Exception): StateHelper<Nothing>()
    companion object
}