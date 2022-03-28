package com.gds.myzap.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.gds.myzap.data.firebase.StoregeFirebase

class StorageRepository {

    private val storage by lazy { StoregeFirebase }
    suspend fun salvandoImagemStorage(
        imagem: Bitmap,
        context: Context,
        atualizaFotoUsuario: (task: Uri) -> Unit
    ) = storage.salvandoImagemStorage(imagem, context, atualizaFotoUsuario)

}