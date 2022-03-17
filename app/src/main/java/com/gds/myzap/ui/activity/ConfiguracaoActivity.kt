package com.gds.myzap.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gds.myzap.databinding.ActivityConfiguracaoBinding
import com.gds.myzap.util.Permissao
import com.gds.myzap.util.dialog

class ConfiguracaoActivity : AppCompatActivity() {
    private val permission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private lateinit var binding: ActivityConfiguracaoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracaoBinding.inflate(layoutInflater)
        Permissao.validarPermissao(permission,this,1)
        setContentView(binding.root)
        initToolBar()
    }

    @SuppressLint("ResourceAsColor")
    private fun initToolBar() {
        val toolbar = binding.toolbarConfig.toolbar
        toolbar.title = "Configurações"
        setSupportActionBar(toolbar)
//            botao voltar padrao do android
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(result in grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                dialog("Permissao necessario","Essa permissao e obrigatoria para o funcionamento do app",false)
            }
        }

    }

}