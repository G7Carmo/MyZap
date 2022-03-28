package com.gds.myzap.ui.viewmodel.activity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.data.repository.AuthRepository
import com.gds.myzap.util.state.UserState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginViewModel : ViewModel() {
    private val repo by lazy { AuthRepository() }
    private var _login = MutableLiveData<UserState<Usuario>>()
    val login: LiveData<UserState<Usuario>> = _login

    init {
        _login.value = UserState.Empty()
    }

    suspend fun login(
        usuario: Usuario,
        context: Context,
    ) = repo.login(usuario, context) { result(it) }

    suspend fun logout() = repo.logout()

    suspend fun resetPassword(
        context: Context,
        email: String
    ) = repo.resetPassword(context, email) { resultResetPass(it) }

    fun result(task: Task<AuthResult>) {
        _login.value = UserState.Loading()
        if (task.isSuccessful) {
            _login.value = UserState.Success()
        } else {
            _login.value = task.exception?.message?.let { UserState.Error(it) }
        }
    }

    fun resultResetPass(task: Task<Void>) {
        _login.value = UserState.Loading()
        if (task.isSuccessful) {
            _login.value = UserState.Success()
        } else {
            _login.value = task.exception?.message?.let { UserState.Error(it) }
        }
    }

}