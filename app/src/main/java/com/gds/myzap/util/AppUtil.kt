package com.gds.myzap.util

import android.icu.text.SimpleDateFormat
import java.util.*

object AppUtil {

    fun dataEHoraAtual(): String {
        val dataFormat = SimpleDateFormat("dd/MM/yy" +"--"+ " HH:mm:ss")
        return dataFormat.format(Date())
    }
}