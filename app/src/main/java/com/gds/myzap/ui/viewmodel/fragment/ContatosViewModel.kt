package com.gds.myzap.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.data.repository.RealtimeRepository
import com.gds.myzap.util.state.UserState
import kotlinx.coroutines.launch

class ContatosViewModel :ViewModel() {
    private val repo by lazy { RealtimeRepository() }

    private val _listContatos = MutableLiveData<UserState<Usuario>>()
    val listContatos : LiveData<UserState<Usuario>> = _listContatos



    suspend fun recuperarDaDosDeContatos(){
        _listContatos.value = UserState.Loading()
        val listaSafe = repo.recuperarDados()
        if (listaSafe.isNotEmpty()){
            _listContatos.value = UserState.Success(listaSafe)
        }else{
            _listContatos.value = UserState.Error("falha ao carregar lista")
        }
    }
}