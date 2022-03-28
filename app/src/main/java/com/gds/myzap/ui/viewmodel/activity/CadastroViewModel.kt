package com.gds.myzap.ui.viewmodel.activity

import android.content.Context
import androidx.lifecycle.*
import com.gds.myzap.data.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.data.repository.AuthRepository
import com.gds.myzap.util.state.UserState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class CadastroViewModel : ViewModel() {
    private val repo by lazy { AuthRepository() }
    private val db by lazy { RealtimeDatabaseFirebase }
    private val userFB by lazy { UsuarioFirebase }


    private var _createUser = MutableLiveData<UserState<Usuario>>()
    val createUser : LiveData<UserState<Usuario>> = _createUser

    init {
        _createUser.value = UserState.Empty()
    }

    fun createUserFirebase(
        user: Usuario,
        context: Context,
    ) = viewModelScope.launch { repo.createUserFirebase(user, context) { result(it) } }

    fun salvandoNoDbRemoto(user: Usuario) = viewModelScope.launch { db.salvarDadosDoCadastro(user) }

    fun salvandoNomeUser(nome: String) = viewModelScope.launch {
        userFB.atualizaNome(nome)
    }

    fun result(task: Task<AuthResult>) {
        _createUser.value = UserState.Loading()
        if (task.isSuccessful) {
            val user = task.result.user
            _createUser.value = UserState.Success(user)
        } else {
            _createUser.value = task.exception?.message?.let { UserState.Error(it) }
        }
    }

}