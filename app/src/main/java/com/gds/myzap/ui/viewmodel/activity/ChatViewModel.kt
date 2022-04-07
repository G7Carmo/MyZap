package com.gds.myzap.ui.viewmodel.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.repository.RealtimeRepository
import com.gds.myzap.util.state.StateMessage
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val repoDb by lazy { RealtimeRepository() }
    private val _mensagens = MutableLiveData<StateMessage>()
    val mensagem : LiveData<StateMessage> = _mensagens

    init {
        _mensagens.value = StateMessage.Loading()
    }

//    fun fetch(idRem : String,idDest:String) = viewModelScope.launch {
//        repoDb.recuperarMensagenParaoChat(idRem,idDest,object : ChildEventListener{
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                val mensagem = snapshot.getValue(Mensagem::class.java)!!
//                _mensagens.value = StateMessage.Success(mensagem)
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//    }
}