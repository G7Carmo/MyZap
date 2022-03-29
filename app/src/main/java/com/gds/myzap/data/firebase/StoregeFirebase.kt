package com.gds.myzap.data.firebase

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import com.gds.myzap.util.localDeArmazenamentoImagemDePerfil
import com.gds.myzap.util.preparandoImagemParaOStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

object StoregeFirebase {
    private val storege by lazy { ConfigFirebase.getStoregeFirebase() }

    suspend fun salvandoImagemStorage(imagem: Bitmap, context: Context,atualizaFotoUsuario : (task : Uri)->Unit) {
        val imagesRef: StorageReference? = localDeArmazenamentoImagemDePerfil(storege.reference)
        val dadosImg = preparandoImagemParaOStorage(imagem)
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

}