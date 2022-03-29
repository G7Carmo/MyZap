package com.gds.myzap.data.repository

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.gds.myzap.data.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.data.model.Usuario
import com.google.firebase.database.ValueEventListener

class RealtimeRepository {
    private val db by lazy { RealtimeDatabaseFirebase }

    suspend fun salvarDadosDoCadastro(usuario: Usuario) = db.salvarDadosDoCadastro(usuario)
    suspend fun recuperandoDados(listner : ValueEventListener) = db.recuperarDadosDoBD(listner)
    fun stopListner(listner: ValueEventListener) = db.pararListner(listner)
}