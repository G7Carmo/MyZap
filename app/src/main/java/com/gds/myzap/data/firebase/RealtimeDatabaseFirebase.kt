package com.gds.myzap.data.firebase

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.data.model.Usuario
import kotlinx.coroutines.launch

object RealtimeDatabaseFirebase {
    internal val db by lazy { ConfigFirebase.getDatabasebFirebase() }

    suspend fun salvarDadosDoCadastro(usuario: Usuario){
        db.child("Usuarios")
            .child(UsuarioFirebase.userKey())
            .setValue(usuario)
    }
    suspend fun recuperarDadosDoBD(): ArrayList<Usuario> {
        val users = ArrayList<Usuario>()
        db.child("Usuarios").child(UsuarioFirebase.userKey()).get().addOnCompleteListener {task->
            val result = task.result
            result?.let {
                val item = it.getValue(Usuario::class.java)!!
                users.add(item)

            }
        }
        return users
    }

}