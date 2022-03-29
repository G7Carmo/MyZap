package com.gds.myzap.data.model

import java.io.Serializable


data class Mensagem(
    var idUsuario: String,
    var mensagem: String,
    var foto : String,
    var dataEHora : String
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
}