package com.gds.myzap.firebase

import com.gds.myzap.model.Usuario

object RealtimeDatabaseFirebase {
    private val db by lazy { ConfigFirebase.getDatabasebFirebase() }

    suspend fun salvarDadosDoCadastro(usuario: Usuario){
        db.child("Usuarios")
            .child(UsuarioFirebase.userKey())
            .setValue(usuario)
    }


}