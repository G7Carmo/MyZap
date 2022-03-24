package com.gds.myzap.firebase

import android.net.Uri
import android.util.Log
import com.gds.myzap.model.Usuario
import com.google.firebase.auth.UserProfileChangeRequest
import java.util.*


object UsuarioFirebase {
    private val auth by lazy { ConfigFirebase.getAuthFirebaseEmeilPassword() }
    suspend fun currentUser() = auth.currentUser

    suspend fun userKey() = auth.uid.toString()
    suspend fun verifyCurrentUser() = auth.currentUser != null
    suspend fun atualizaFoto(uri: Uri): Boolean {
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

    suspend fun atualizaNome(nome: String): Boolean {
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

    suspend fun atualizarUser(user: Usuario) {
        val id = userKey()
        val db = ConfigFirebase.getDatabasebFirebase()
        val userRef = db.child("Usuarios").child(userKey())
        val userMap = converterParaMap(user)
        userRef.updateChildren(userMap)
    }

    private fun converterParaMap(user: Usuario): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()
        map.put("email", user.email)
        map.put("nome", user.nome)
        map.put("foto", user.foto)
        return map
    }

    suspend fun dadosUsuarioLogado(): Usuario.Builder {
        val fbUser = currentUser()!!
        val user = Usuario.Builder
        user.nome(fbUser.displayName.toString())
        user.email(fbUser.email.toString())
        if (fbUser.photoUrl == null) {
            user.foto("")
        } else {
            user.foto(fbUser.photoUrl.toString())
        }
        return user
    }
}