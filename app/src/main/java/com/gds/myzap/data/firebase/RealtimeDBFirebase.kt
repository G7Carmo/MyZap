package com.gds.myzap.data.firebase

import android.util.Log
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.model.Usuario
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.ValueEventListener

object RealtimeDBFirebase {
    private val db by lazy { ConfigFirebase.getDatabasebFirebase() }
    suspend fun salvarDadosDoCadastro(usuario: Usuario){
        db.child("Usuarios")
            .child(UsuarioFirebase.userKey())
            .setValue(usuario)
    }
    suspend fun listaDeUsuariosCadastradosNoFirebase(listener : ValueEventListener){
        val ref = db.child("Usuarios")
        ref.addValueEventListener(listener)
    }
    fun stopEventListner(listener: ValueEventListener){
        db.removeEventListener(listener)
    }
    fun salvarMensagemChat(idRem : String, idDest:String, msg : Mensagem){
        db.child("Mensagem").child(idRem).child(idDest).push().setValue(msg)
    }
    fun recuperarMensagenParaoChat(idRem: String, idDest:String, listener: ChildEventListener){
        db.child("Mensagens").child(idRem).child(idDest).addChildEventListener(listener)
    }
    fun stopEventListner(listener: ChildEventListener){
        db.removeEventListener(listener)
    }
}