package com.gds.myzap.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Permissao {

    fun validarPermissao(permission: Array<String>,activity:Activity,requesCode : Int):Boolean{
        val listaDePermissoesNegadas = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= 23){
            for(permissao in permission){
                val temPermissao = ContextCompat.checkSelfPermission(
                    activity,
                    permissao
                ) == PackageManager.PERMISSION_GRANTED
                if (!temPermissao) listaDePermissoesNegadas.add(permissao)
            }
            if (listaDePermissoesNegadas.isEmpty()) return true
            val novasPermissoes : Array<String> = listaDePermissoesNegadas.toTypedArray()
            ActivityCompat.requestPermissions(activity,novasPermissoes,requesCode)
        }
        return true
    }

}