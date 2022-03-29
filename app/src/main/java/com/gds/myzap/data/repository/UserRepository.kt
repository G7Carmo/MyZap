package com.gds.myzap.data.repository

import android.net.Uri
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Usuario

class UserRepository {
    private val userConnected by lazy { UsuarioFirebase }
    suspend fun currentUser() = userConnected.currentUser()
    suspend fun userKey() = userConnected.userKey()
    suspend fun verifyCurrentUser() = userConnected.verifyCurrentUser()
    suspend fun atualizaFoto(uri: Uri): Boolean = userConnected.atualizaFotoUsuarioLogado(uri)
    suspend fun atualizaNome(nome: String): Boolean = userConnected.atualizaNomeUsuarioLogado(nome)
    suspend fun atualizarUser(user: Usuario) = userConnected.atualizarDadosUsuarioLogado(user)
    suspend fun dadosUsuarioLogado(): Usuario = userConnected.getTodosOsDadosDoUsuarioLogado()
}