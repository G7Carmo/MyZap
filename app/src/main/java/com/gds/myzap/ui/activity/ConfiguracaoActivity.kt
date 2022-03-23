package com.gds.myzap.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.lifecycleScope
import com.gds.myzap.databinding.ActivityConfiguracaoBinding
import com.gds.myzap.firebase.StoregeFirebase
import com.gds.myzap.util.Permissao
import com.gds.myzap.util.dialog
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

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
        listners()
    }

    @SuppressLint("ResourceAsColor")
    private fun initToolBar() {
        val toolbar = binding.toolbarConfig.toolbar
        toolbar.title = "Configurações"
        setSupportActionBar(toolbar)
//            botao voltar padrao do android
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun listners() = with(binding){
        imgBtnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null){
                startActivityForResult(intent, Companion.SELECAO_CAMERA)
            }
        }
        imgBtnGaleria.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            if (intent.resolveActivity(packageManager) != null){
                startActivityForResult(intent, SELECAO_GALERIA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            var imagem : Bitmap? = null
            try {
                when(requestCode){
                    SELECAO_CAMERA ->{
                        imagem = data?.extras?.get("data") as Bitmap
                    }
                    SELECAO_GALERIA ->{
                        val localDaImagem = data?.data
                        imagem = MediaStore.Images.Media.getBitmap(contentResolver,localDaImagem)
                    }
                }
                if (imagem != null) {
                    binding.imgPerfilPadrao.setImageBitmap(imagem)
                    salvandoNoFIrebase(imagem)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    private fun salvandoNoFIrebase(imagem: Bitmap)= lifecycleScope.launch {
        StoregeFirebase.saveImage(imagem,this@ConfiguracaoActivity)
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

    companion object {
        const val SELECAO_CAMERA = 100
        const val SELECAO_GALERIA = 200
    }

}