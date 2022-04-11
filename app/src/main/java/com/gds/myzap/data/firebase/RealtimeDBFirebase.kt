package com.gds.myzap.data.firebase

import android.util.Log
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.model.Usuario
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
    fun recuperarMensagenParaoChat(idDest:String,result: (msg : Mensagem)->Unit){
        db.child("Mensagem")
            .child(UsuarioFirebase.userKey())
            .child(idDest).addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val mensagem = snapshot.getValue(Mensagem::class.java)!!
                    result(mensagem)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    fun stopEventListner(listener: ChildEventListener){
        db.removeEventListener(listener)
    }
}