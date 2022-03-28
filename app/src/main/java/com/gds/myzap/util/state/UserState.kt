package com.gds.myzap.util.state

import com.google.firebase.auth.FirebaseUser

sealed class UserState<T>(
    var data: Any? = null,
    var message: String? = ""
){
    class Success<T>(data: Any? = null) : UserState<T>(data)
    class Error<T>(message: String) : UserState<T>(message)
    class Empty<T>() : UserState<T>()
    class Loading<T>() : UserState<T>()
}
