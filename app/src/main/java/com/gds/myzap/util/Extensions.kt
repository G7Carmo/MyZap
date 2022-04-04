package com.gds.myzap.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gds.myzap.data.firebase.AuthFirebase
import com.gds.myzap.data.firebase.StoregeFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*
import javax.microedition.khronos.egl.EGLDisplay

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
    ).show()
}
fun Fragment.message(mensagem: String, duracao: Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        context,
        mensagem,
        duracao
    ).show()
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

fun StoregeFirebase.localDeArmazenamentoImagemDePerfil(storage: StorageReference): StorageReference {
    return storage
        .child("Imagens")
        .child(UsuarioFirebase.userKey())
        .child("perfil.jpeg")
}

fun StoregeFirebase.preparandoImagemParaOStorage(imagem: Bitmap): ByteArray {
    val baos = ByteArrayOutputStream()
    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos)
    return baos.toByteArray()
}

@SuppressLint("SimpleDateFormat")
fun Any.dataEHoraAtual(): String =
    SimpleDateFormat("dd/MM/yy" +"--"+ "HH:mm:ss").format(Date())

fun Usuario.toMap(): MutableMap<String, Any> {
    val map = mutableMapOf<String, Any>()
    map["email"] = this.email
    map["nome"] = this.nome
    map["foto"] = this.foto
    return map
}
fun Any.textIsValid(edit: TextInputEditText,label : TextInputLayout,error : String): String {
    val safeText = edit.text.toString().trim()
    if (safeText.isNotEmpty()){
        return safeText
    }else{
        label.isErrorEnabled = true
        label.error = error
    }
    return safeText
}
