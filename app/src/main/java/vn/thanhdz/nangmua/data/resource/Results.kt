package vn.thanhdz.nangmua.data.resource

abstract class Results<T> {
    class Success<T>(val data: T) : Results<T>()
    class Error<T>(val e: Exception) : Results<T>()
}