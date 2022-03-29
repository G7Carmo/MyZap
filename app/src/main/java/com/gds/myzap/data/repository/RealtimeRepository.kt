package com.gds.myzap.data.repository

import com.gds.myzap.data.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.model.Usuario
import com.google.firebase.database.ValueEventListener

class RealtimeRepository {
    private val db by lazy { RealtimeDatabaseFirebase }

    suspend fun salvarDadosDoCadastro(usuario: Usuario) = db.salvarDadosDoCadastro(usuario)
    suspend fun recuperandoDados(listner : ValueEventListener) = db.listaDeUsuariosCadastradosNoFirebase(listner)
    fun stopListner(listner: ValueEventListener) = db.pararListner(listner)
    fun salvarMensagem(idRem : String,idDest:String,msg : Mensagem) = db.salvarMensagemChat(idRem, idDest, msg)
}