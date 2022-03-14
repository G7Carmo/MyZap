package com.gds.myzap.util

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun Context.nextScreen(activity : AppCompatActivity){
    Intent(this,activity::class.java).apply {
        startActivity(this)
    }
}