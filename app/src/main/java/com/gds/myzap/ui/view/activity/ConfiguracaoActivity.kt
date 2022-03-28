package com.gds.myzap.ui.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase.atualizaFoto
import com.gds.myzap.data.firebase.UsuarioFirebase.atualizarUser
import com.gds.myzap.data.firebase.UsuarioFirebase.dadosUsuarioLogado
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.databinding.ActivityConfiguracaoBinding
import com.gds.myzap.ui.viewmodel.activity.ConfiguracaoViewModel
import com.gds.myzap.util.Permissao
import com.gds.myzap.util.dialog
import com.gds.myzap.util.message
import com.gds.myzap.util.state.UserState
import kotlinx.coroutines.launch

class ConfiguracaoActivity : AppCompatActivity() {
    private val permission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private lateinit var binding: ActivityConfiguracaoBinding
    private lateinit var userLogado: Usuario
    private val viewModel: ConfiguracaoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracaoBinding.inflate(layoutInflater)
        Permissao.validarPermissao(permission, this, 1)
        setContentView(binding.root)
        initToolBar()
        recuperandoDadosDoUser()
        listners()
        observer()

    }


    @SuppressLint("ResourceAsColor")
    private fun initToolBar() {
        val toolbar = binding.toolbarConfig.toolbar
        toolbar.title = "Configurações"
        setSupportActionBar(toolbar)
//            botao voltar padrao do android
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun recuperandoDadosDoUser() = lifecycleScope.launch {
        val usuario = UsuarioFirebase.currentUser()
        userLogado = dadosUsuarioLogado()
        val url = usuario?.photoUrl
        if (url != null) {
            Glide
                .with(this@ConfiguracaoActivity)
                .load(url)
                .into(binding.imgPerfilPadrao)
        } else {
            binding.imgPerfilPadrao.setImageResource(R.drawable.padrao)
        }
        binding.editNomeConfiguracao.setText(usuario?.displayName ?: "Sem Nome")
    }


    private fun listners() = with(binding) {
        imgBtnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, SELECAO_CAMERA)
            }
        }
        imgBtnGaleria.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, SELECAO_GALERIA)
            }
        }
        iconEditConfiguracao.setOnClickListener {
            viewModel.atualizaNomeNomeNoBD(binding.editNomeConfiguracao.text.toString())

        }
    }

//


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            var imagem: Bitmap? = null
            try {
                when (requestCode) {
                    SELECAO_CAMERA -> {
                        imagem = data?.extras?.get("data") as Bitmap
                    }
                    SELECAO_GALERIA -> {
                        val localDaImagem = data?.data
                        imagem = MediaStore.Images.Media.getBitmap(contentResolver, localDaImagem)
                    }
                }
                if (imagem != null) {
                    binding.imgPerfilPadrao.setImageBitmap(imagem)
                    viewModel.salvandoNoFIrebase(imagem) { atualizaFotoUsuario(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                dialog(
                    "Permissao necessario",
                    "Essa permissao e obrigatoria para o funcionamento do app",
                    false
                )
            }
        }

    }

    private fun observer() {
        viewModel.userLogado.observe(this, Observer { state ->
            when (state) {
                is UserState.Success -> {
                    atualizandoUsuario()
                }
                is UserState.Error -> {
                    dialog("Falha","Nome nao atualizado")
                }
                else -> {}
            }
        })
    }

    private fun atualizandoUsuario() = lifecycleScope.launch {
        userLogado = viewModel.atualizarDadosDoUsuario(userLogado)
        userLogado.nome = binding.editNomeConfiguracao.text.toString()
        atualizarUser(userLogado)
        message("Salvo com Sucesso")

    }

    private fun atualizaFotoUsuario(url: Uri) = lifecycleScope.launch {
        if (atualizaFoto(url)) {
            userLogado.foto = url.toString()
            atualizarUser(userLogado)
            message("Foto Alterada")
        }
    }
        companion object {
        const val SELECAO_CAMERA = 100
        const val SELECAO_GALERIA = 200
    }

}