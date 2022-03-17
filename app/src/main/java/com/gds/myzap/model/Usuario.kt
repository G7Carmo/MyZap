package com.gds.myzap.model

import com.google.firebase.database.Exclude


data class Usuario(
    val nome: String,
    val email: String,
    val senha: String
)