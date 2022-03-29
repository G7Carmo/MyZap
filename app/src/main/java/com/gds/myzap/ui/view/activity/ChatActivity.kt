package com.gds.myzap.ui.view.activity

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.data.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    private lateinit var userdestinatario : Usuario
    private lateinit var idUserDestinatario : String
    private lateinit var idUserRemetente : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolBar()
        listners()
        bundleValeus()
    }

    private fun bundleValeus() {
        val extras = intent.extras
        if (extras != null){
            userdestinatario = extras.getSerializable("chatContato") as Usuario
            binding.textNomeUserChat.text = userdestinatario.nome
            val foto = userdestinatario.foto
            if (foto != null){
                val uriFoto = Uri.parse(userdestinatario.foto)
                Glide.with(this)
                    .load(uriFoto)
                    .into(binding.circleImagemChat)
            }else{
                binding.circleImagemChat.setImageResource(R.drawable.padrao)
            }
            idUserDestinatario = userdestinatario.nome + userdestinatario.email

        }

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
            enviarMensagem()
        }
        containerChat.imgViewCameraChat.setOnClickListener {
            //TODO - Acao de abrir a galeria ou camera para enviar imagem
        }
    }
    fun enviarMensagem(){
        val txtMensagem = binding.containerChat.editTextMensagemChat.text.toString()
        if(!txtMensagem.isEmpty()){
            val mensagem = Mensagem()
            mensagem.idUsuario = UsuarioFirebase.userKey()
            mensagem.mensagem = txtMensagem
            salvarMensagem(UsuarioFirebase.userKey(),idUserDestinatario,mensagem)

        }
    }
    fun salvarMensagem(idRemetente : String,idDestinatario : String,mensagem : Mensagem){
        RealtimeDatabaseFirebase.salvarMensagemChat(idRemetente,idDestinatario,mensagem)
    }

}