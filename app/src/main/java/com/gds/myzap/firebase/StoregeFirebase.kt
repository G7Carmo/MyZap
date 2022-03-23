package com.gds.myzap.firebase

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

object StoregeFirebase {
    private val storege by lazy { ConfigFirebase.getStoregeFirebase() }

    suspend fun saveImage(imagem : Bitmap,context:Context){
        val baos =  ByteArrayOutputStream()
        val dadosImg = baos.toByteArray()
        imagem.compress(Bitmap.CompressFormat.JPEG,70,baos)

        storege
            .child("Imagens")
            .child("Perfil")
            .child(AuthFirebase.userKey())
            .child("perfil.jpeg")

        val uploadTask = storege.putBytes(dadosImg)
        uploadTask.addOnFailureListener {
            Toast.makeText(context,"Falha ao savar imagem ",Toast.LENGTH_LONG).show()
        }
        uploadTask.addOnSuccessListener {
            Toast.makeText(context,"Sucesso  ao savar imagem ",Toast.LENGTH_LONG).show()

        }
    }

}