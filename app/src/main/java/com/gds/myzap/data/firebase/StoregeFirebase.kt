package com.gds.myzap.data.firebase

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

object StoregeFirebase {
    private val storege by lazy { ConfigFirebase.getStoregeFirebase() }

    suspend fun salvandoImagemStorage(imagem: Bitmap, context: Context,atualizaFotoUsuario : (task : Uri)->Unit) {
        val imagesRef: StorageReference? = preparandoLocalNoStorage()
        val dadosImg = preparandoImagem(imagem)
        val uploadTask = imagesRef?.putBytes(dadosImg)
        uploadTask?.addOnFailureListener {
            Toast.makeText(context, "Falha ao savar imagem ", Toast.LENGTH_LONG).show()
        }?.addOnSuccessListener { taskSnapshot ->
            Toast.makeText(context, "Sucesso  ao savar imagem ", Toast.LENGTH_LONG).show()
            imagesRef.downloadUrl.addOnCompleteListener {
                val url = it.result
                atualizaFotoUsuario(url)
            }
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
            .child(UsuarioFirebase.userKey())
            .child("perfil.jpeg")
        return imagesRef
    }

}