package com.gds.myzap.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gds.myzap.data.firebase.AuthFirebase
import com.gds.myzap.data.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.data.firebase.StoregeFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

fun Context.nextScreen(activity : AppCompatActivity){
    Intent(this,activity::class.java).apply {
        startActivity(this)
    }
}

fun AppCompatActivity.dialog(titulo: String, mensagem: String) {
    AlertDialog.Builder(this)
        .setTitle(titulo)
        .setMessage(mensagem)
        .setPositiveButton("OK") { dialogInterface, i ->
            finish()
        }
        .create()
        .show()
}
fun AppCompatActivity.dialog(titulo: String, mensagem: String,cancelable : Boolean = true) {
    AlertDialog.Builder(this)
        .setTitle(titulo)
        .setMessage(mensagem)
        .setCancelable(cancelable)
        .setPositiveButton("OK") { dialogInterface, i ->
            finish()
        }
        .create()
        .show()
}
fun AppCompatActivity.message(mensagem: String, duracao: Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        this,
        mensagem,
        duracao
    )
}
fun Fragment.message(mensagem: String, duracao: Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        context,
        mensagem,
        duracao
    )
}



fun View.show(){
    visibility = View.VISIBLE
}
fun View.hide(){
    visibility = View.GONE
}

fun AuthFirebase.dialog(context: Context, titulo: String, mensagem: String) {
    AlertDialog.Builder(context)
        .setTitle(titulo)
        .setMessage(mensagem)
        .setPositiveButton("OK") { dialogInterface, i ->
        }
        .create()
        .show()
}

fun StoregeFirebase.localDeArmazenamentoImagemDePerfil(storage:StorageReference): StorageReference {
    val storageRef = storage
    val imagesRef: StorageReference = storageRef
        .child("Imagens")
        .child(UsuarioFirebase.userKey())
        .child("perfil.jpeg")
    return imagesRef
}

fun StoregeFirebase.preparandoImagemParaOStorage(imagem: Bitmap): ByteArray {
    val baos = ByteArrayOutputStream()
    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos)
    val dadosImg = baos.toByteArray()
    return dadosImg
}