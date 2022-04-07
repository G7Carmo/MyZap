package com.gds.myzap.data.repository

import com.gds.myzap.data.firebase.RealtimeDBFirebase
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.model.Usuario
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.ValueEventListener

class RealtimeRepository {
    private val db by lazy { RealtimeDBFirebase }

    suspend fun salvarDadosDoCadastro(usuario: Usuario) = db.salvarDadosDoCadastro(usuario)
    suspend fun recuperandoDados(listner : ValueEventListener) = db.listaDeUsuariosCadastradosNoFirebase(listner)
    fun stopListner(listner: ValueEventListener) = db.stopEventListner(listner)
    fun salvarMensagem(idRem : String,idDest:String,msg : Mensagem) = db.salvarMensagemChat(idRem, idDest, msg)
    fun recuperarMensagenParaoChat(idRem: String, idDest:String, listener: ChildEventListener) = db.recuperarMensagenParaoChat(idRem, idDest, listener)
}