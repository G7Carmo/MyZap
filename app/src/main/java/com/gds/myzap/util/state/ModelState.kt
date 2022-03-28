package com.gds.myzap.util.state

sealed class ModelState<T>(
    val data : T? = null,
    val message : String? = ""
){
    class Success<T>(data: T,message: String?) : ModelState<T>(data, message)
    class Error<T>(data: T?,message: String) : ModelState<T>(data, message)
    class Empty<T>() : ModelState<T>()
    class Loading<T>() : ModelState<T>()
}
