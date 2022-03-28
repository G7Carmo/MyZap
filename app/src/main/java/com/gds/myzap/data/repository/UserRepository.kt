package com.gds.myzap.data.repository

import android.net.Uri
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Usuario

class UserRepository {
    private val userConnected by lazy { UsuarioFirebase }
    suspend fun currentUser() = userConnected.currentUser()
    suspend fun userKey() = userConnected.userKey()
    suspend fun verifyCurrentUser() = userConnected.verifyCurrentUser()
    suspend fun atualizaFoto(uri: Uri): Boolean = userConnected.atualizaFoto(uri)
    suspend fun atualizaNome(nome: String): Boolean = userConnected.atualizaNome(nome)
    suspend fun atualizarUser(user: Usuario) = userConnected.atualizarUser(user)
    suspend fun dadosUsuarioLogado(): Usuario = userConnected.dadosUsuarioLogado()
}