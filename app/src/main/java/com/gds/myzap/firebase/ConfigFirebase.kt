package com.gds.myzap.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object ConfigFirebase {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var dbFirebase : DatabaseReference = FirebaseDatabase.getInstance().reference
    private var storage : StorageReference = FirebaseStorage.getInstance().reference
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
    fun getStoregeFirebase(): StorageReference {
        if (storage == null){
            storage = FirebaseStorage.getInstance().reference
        }
        return storage
    }
}