package com.gds.myzap.firebase.db

import android.widget.Toast
import com.gds.myzap.firebase.ConfigFirebase
import com.gds.myzap.firebase.auth.AuthFirebase
import com.gds.myzap.model.Usuario
import com.google.android.gms.tasks.Task

object RealtimeDatabaseFirebase {
    private val db by lazy { ConfigFirebase.getDatabasebFirebase() }

    suspend fun salvarDadosDoCadastro(usuario: Usuario){
        db.child("Usuarios")
            .child(AuthFirebase.userKey())
            .setValue(usuario)
    }


}