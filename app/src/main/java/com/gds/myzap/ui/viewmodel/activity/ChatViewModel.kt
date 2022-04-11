package com.gds.myzap.ui.viewmodel.activity

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.repository.RealtimeRepository
import com.gds.myzap.util.state.StateMessage

class ChatViewModel : ViewModel() {
    private val repoDb by lazy { RealtimeRepository() }
    private val _mensagens = MutableLiveData<StateMessage>()
    val mensagem : LiveData<StateMessage> = _mensagens

    init {
        loanding()
    }

    private fun loanding() {
        _mensagens.value = StateMessage.Loading()
        Handler(Looper.myLooper()!!).postDelayed({
                 _mensagens.value = StateMessage.Error(null,"Falha na busca das mensagens ou chat sem mensagens")
        }, 5000)
    }

    fun carregarMsgs(idDest:String) = repoDb.recuperarMensagenParaoChat(idDest){result(it)}

    private fun result(msg: Mensagem) {
        _mensagens.value = StateMessage.Success(msg)
    }

}