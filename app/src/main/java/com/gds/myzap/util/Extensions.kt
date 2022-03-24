package com.gds.myzap.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gds.myzap.model.Usuario
import java.util.*
import kotlin.collections.HashMap

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
