package com.gds.myzap.data.model

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.io.Serializable
import java.util.*


data class Mensagem(
    var dataEHora : String,
    var foto : String,
    var idUsuario: String,
    var mensagem: String
): Serializable{
    constructor() : this("","","","")

    object Builder{
        val msgBuilder = Mensagem("","","","")
        fun id(id: String){
            msgBuilder.idUsuario = id
        }
        fun mensagem(mensagem: String){
            msgBuilder.mensagem = mensagem
        }
        fun foto(foto: String){
            msgBuilder.foto = foto
        }
        fun dataEHora(dataEHora: String){
            msgBuilder.dataEHora = dataEHora
        }
        fun builder() = msgBuilder
    }
    @SuppressLint("SimpleDateFormat")
    fun dataEHoraAtual(): String =
        SimpleDateFormat("dd/MM/yy" +"--"+ "HH:mm:ss").format(Date())
}