package com.gds.myzap.data.repository

import android.content.Context
import com.gds.myzap.data.firebase.AuthFirebase
import com.gds.myzap.data.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class AuthRepository {
    private val auth by lazy { AuthFirebase }
    suspend fun createUserFirebase(
        user: Usuario,
        context: Context,
        result: (task: Task<AuthResult>) -> Unit
    ) = auth.createUserFirebase(user, context, result)

    suspend fun login(
        usuario: Usuario,
        context: Context,
        result: (task: Task<AuthResult>) -> Unit
    ) = auth.login(usuario, context, result)

    suspend fun logout() = auth.logout()
    suspend fun resetPassword(
        context: Context,
        email: String,
        sucesso: (task: Task<Void>) -> Unit
    ) = auth.resetPassword(context, email, sucesso)
}