package com.gds.myzap.data.model

import java.io.Serializable
import java.util.*


data class Usuario(
    var nome: String,
    var email: String,
    var senha: String,
    var foto : String
): Serializable{
    constructor() : this("","","","")

    object Builder{
        val userBuider = Usuario("","","","")
        fun nome(nome: String){
            userBuider.nome = nome
        }
        fun email(email: String){
            userBuider.email = email
        }
        fun senha(senha: String){
            userBuider.senha = senha
        }
        fun foto(foto: String){
            userBuider.foto = foto
        }

        fun builder() = userBuider
    }
}