package com.gds.myzap.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object ConfigFirebase {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var dbFirebase : DatabaseReference

    fun getAuthFirebaseEmeilPassword(): FirebaseAuth {
        if (auth == null){
            auth = FirebaseAuth.getInstance()
        }
        return auth
    }
    fun getDatabasebFirebase(): DatabaseReference {
        if (dbFirebase == null){
            dbFirebase = FirebaseDatabase.getInstance().reference
        }
        return dbFirebase
    }
}