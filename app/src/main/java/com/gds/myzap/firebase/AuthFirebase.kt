package com.gds.myzap.firebase

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.gds.myzap.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

object AuthFirebase {
    private val auth by lazy { ConfigFirebase.getAuthFirebaseEmeilPassword() }

    suspend fun createUserFirebase(
        user: Usuario,
        context: Context,
        result: (task: Task<AuthResult>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(
            user.email,
            user.senha
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result(task)
            } else {
                var msg = " "
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    msg = "Digite uma senha Mais Forte"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    msg = "Formato de email invalido"
                } catch (e: FirebaseAuthUserCollisionException) {
                    msg = "Endereço de usuario ja cadastrado"
                } catch (e: Exception) {
                    msg = " Erro ao cadastrar usuario " + e.message
                    e.printStackTrace()
                }
                dialog(context, "Falha de Cadastro", msg)
            }
        }
    }

    suspend fun login(
        usuario: Usuario,
        context: Context,
        result: (task: Task<AuthResult>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(
            usuario.email,
            usuario.senha
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result(task)
            } else {
                var msg = ""
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    msg = "Email ou senha incorretos"
                } catch (e: FirebaseAuthInvalidUserException) {
                    msg = "Email incorreto ou nao cadastrado "
                } catch (e: Exception) {
                    msg = "Falha ao logar " + e.message
                    e.printStackTrace()
                }
                dialog(context, "Falha Login", msg)
            }
        }
    }
    suspend fun logout() = auth.signOut()

    suspend fun currentUser() = auth.currentUser

    suspend fun userKey() = auth.uid.toString()
    suspend fun verifyCurrentUser() = auth.currentUser != null
    suspend fun resetPassword(context: Context,email: String, sucesso: (task: Task<Void>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    sucesso(authTask)
                } else {
                    var msg = ""
                    try {
                        throw authTask.exception!!
                    }catch (e : FirebaseAuthInvalidCredentialsException){
                        msg = "Email incorretos"
                    }catch (e : FirebaseAuthException){
                        msg = "Falha ao logar " + e.message
                    }
                    Toast.makeText(context, "$msg", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun dialog(context: Context, titulo: String, mensagem: String) {
        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setPositiveButton("OK") { dialogInterface, i ->
            }
            .create()
            .show()
    }
}