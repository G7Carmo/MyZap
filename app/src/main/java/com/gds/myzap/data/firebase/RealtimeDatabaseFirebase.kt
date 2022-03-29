package com.gds.myzap.data.firebase

import android.content.Context
import android.media.MediaPlayer
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.data.model.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

object RealtimeDatabaseFirebase {
    internal val db by lazy { ConfigFirebase.getDatabasebFirebase() }


    suspend fun salvarDadosDoCadastro(usuario: Usuario){
        db.child("Usuarios")
            .child(UsuarioFirebase.userKey())
            .setValue(usuario)
    }
    suspend fun recuperarDadosDoBD(listener : ValueEventListener){
        val ref = db.child("Usuarios")
        ref.addValueEventListener(listener)
    }
    fun pararListner(listener: ValueEventListener){
        db.removeEventListener(listener)
    }
}