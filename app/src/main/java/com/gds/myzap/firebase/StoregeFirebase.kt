package com.gds.myzap.firebase

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

object StoregeFirebase {
    private val storege by lazy { ConfigFirebase.getStoregeFirebase() }

//    suspend fun saveImage(imagem : Bitmap,context:Context){
//        val baos =  ByteArrayOutputStream()
//        imagem.compress(Bitmap.CompressFormat.JPEG,70,baos)
//        val dadosImg = baos.toByteArray()
//
//        storege
//            .child("Imagens")
//            .child("Perfil")
//            .child(AuthFirebase.userKey())
//            .child("perfil.jpeg")
//
//        val uploadTask = storege.putBytes(dadosImg)
//        uploadTask.addOnFailureListener {
//            Toast.makeText(context,"Falha ao savar imagem ",Toast.LENGTH_LONG).show()
//        }
//        uploadTask.addOnSuccessListener {
//            Toast.makeText(context,"Sucesso  ao savar imagem ",Toast.LENGTH_LONG).show()
//
//        }
//    }

    suspend fun salvandoImagemStorage(imagem : Bitmap,context:Context){
        val imagesRef: StorageReference? = preparandoLocalNoStorage()
        val dadosImg = preparandoImagem(imagem)
        val uploadTask = imagesRef?.putBytes(dadosImg)
        uploadTask?.addOnFailureListener {
            Toast.makeText(context,"Falha ao savar imagem ",Toast.LENGTH_LONG).show()
        }?.addOnSuccessListener { taskSnapshot ->
            Toast.makeText(context,"Sucesso  ao savar imagem ",Toast.LENGTH_LONG).show()
            // ...
        }
    }

    private fun preparandoImagem(imagem: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val dadosImg = baos.toByteArray()
        return dadosImg
    }

    private suspend fun preparandoLocalNoStorage(): StorageReference? {
        var storageRef = storege.reference
        var imagesRef: StorageReference? = storageRef
            .child("Imagens")
            .child(AuthFirebase.userKey())
            .child("perfil.jpeg")
        return imagesRef
    }

}