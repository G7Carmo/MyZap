package com.gds.myzap.util.state

import com.google.firebase.auth.FirebaseUser

sealed class UserState<T>(
    val data: T? = null,
    val message: String? = ""
){
    class Success<T>(data: T? = null) : UserState<T>(data)
    class Error<T>(data: T? = null,message: String?) : UserState<T>(data,message)
    class Empty<T>() : UserState<T>()
    class Loading<T>() : UserState<T>()
}
