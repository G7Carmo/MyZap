package com.gds.myzap.data.firebase

import android.net.Uri
import android.util.Log
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.util.toMap
import com.google.firebase.auth.UserProfileChangeRequest


object UsuarioFirebase {
    private val auth by lazy { ConfigFirebase.getAuthFirebaseEmeilPassword() }
    fun currentUser() = auth.currentUser

    fun userKey() = auth.uid.toString()
    suspend fun verifyCurrentUser() = auth.currentUser != null
    suspend fun atualizaFotoUsuarioLogado(uri: Uri): Boolean {
        val user = currentUser()
        try {
            val profile = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
            user?.updateProfile(profile)?.addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d("AtualizaFoto", "Erro")
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun atualizaNomeUsuarioLogado(nome: String): Boolean {
        val user = currentUser()
        try {
            val profile = UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build()
            user?.updateProfile(profile)?.addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d("AtualizaNome", "Erro")
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun atualizarDadosUsuarioLogado(user: Usuario) {
        val db = ConfigFirebase.getDatabasebFirebase()
        val userRef = db.child("Usuarios").child(userKey())
        userRef.updateChildren(user.toMap())
    }

    private fun deUsuarioParaMap(user: Usuario): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()
        map.put("email", user.email)
        map.put("nome", user.nome)
        map.put("foto", user.foto)
        return map
    }


    fun getTodosOsDadosDoUsuarioLogado(): Usuario {
        val fbUser = currentUser()!!
        val user = Usuario.Builder
        user.nome(fbUser.displayName.toString())
        user.email(fbUser.email.toString())
        if (fbUser.photoUrl == null) {
            user.foto("")
        } else {
            user.foto(fbUser.photoUrl.toString())
        }
        return user.builder()
    }


}