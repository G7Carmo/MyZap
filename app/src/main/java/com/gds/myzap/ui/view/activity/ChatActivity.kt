package com.gds.myzap.ui.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gds.myzap.R
import com.gds.myzap.databinding.ActivityChatBinding
import de.hdodenhof.circleimageview.CircleImageView

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolBar()
        listners()
    }

    @SuppressLint("ResourceType")
    private fun initToolBar() {
        val toolbar = binding.toolbarChat
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun listners() = with(binding){
        circleImagemChat.setOnClickListener {
            //TODO - Tela da imagem de contato expandida
        }
        textNomeUserChat.setOnClickListener {
            //TODO - Tela de informações do contato
        }
        containerChat.fabEnviarMensagemChat.setOnClickListener {
            //TODO - Acao de enviar mensagem
        }
        containerChat.imgViewCameraChat.setOnClickListener {
            //TODO - Acao de abrir a galeria ou camera para enviar imagem
        }
    }

}