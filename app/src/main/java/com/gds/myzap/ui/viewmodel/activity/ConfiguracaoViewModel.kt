package com.gds.myzap.ui.viewmodel.activity

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.*
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.data.repository.StorageRepository
import com.gds.myzap.data.repository.UserRepository
import com.gds.myzap.util.state.UserState
import kotlinx.coroutines.launch

class ConfiguracaoViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo by lazy { UserRepository() }
    private val storage by lazy { StorageRepository() }

    private val _userLogado = MutableLiveData<UserState<Usuario>>()
    val userLogado: LiveData<UserState<Usuario>> get() = _userLogado

    @SuppressLint("StaticFieldLeak")
    val context = application.applicationContext

   fun atualizaNomeNomeNoBD(nome:String)= viewModelScope.launch{
       _userLogado.value = UserState.Loading()
       if (nome.isNotEmpty()){
           if (userRepo.atualizaNome(nome)){
                _userLogado.value = UserState.Success()
           }else{
               _userLogado.value = UserState.Error("Falha ao atualizar nome")
           }
       }
   }

    suspend fun atualizarDadosDoUsuario(usuario: Usuario): Usuario {
        return userRepo.dadosUsuarioLogado()
    }

    fun salvandoNoFIrebase(imagem: Bitmap,result : (uri : Uri)->Unit) = viewModelScope.launch {
        storage.salvandoImagemStorage(imagem,context){result(it)}
    }

}