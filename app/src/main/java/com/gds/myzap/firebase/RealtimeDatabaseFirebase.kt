package com.gds.myzap.firebase

import com.gds.myzap.model.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object RealtimeDatabaseFirebase {
    internal val db by lazy { ConfigFirebase.getDatabasebFirebase() }

    suspend fun salvarDadosDoCadastro(usuario: Usuario){
        db.child("Usuarios")
            .child(UsuarioFirebase.userKey())
            .setValue(usuario)
    }

}