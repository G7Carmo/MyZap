package com.gds.myzap.ui.view.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.data.firebase.ConfigFirebase
import com.gds.myzap.data.firebase.RealtimeDBFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase.userKey
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.databinding.ActivityChatBinding
import com.gds.myzap.ui.view.adapter.MensagensAdapter
import com.gds.myzap.ui.viewmodel.activity.ChatViewModel
import com.gds.myzap.util.*
import com.gds.myzap.util.state.StateMessage
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.util.logging.Handler

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    private lateinit var userdestinatario : Usuario
    private lateinit var idUserDestinatario : String
    private lateinit var mensagensAdapter : MensagensAdapter
    private var listaMensagens : ArrayList<Mensagem> = arrayListOf()
    private val viewModel : ChatViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundleValeus()
        setupList()
        listners()
        observers()


    }
    private fun bundleValeus() {
        val extras = intent.extras
        if (extras != null){
            userdestinatario = extras.getSerializable("chatContato") as Usuario
            binding.textNomeUserChat.text = userdestinatario.nome
            if ( userdestinatario.foto != null){
                val uriFoto = Uri.parse(userdestinatario.foto)
                Glide.with(this)
                    .load(uriFoto)
                    .into(binding.circleImagemChat)
            }else{
                binding.circleImagemChat.setImageResource(R.drawable.padrao)
            }
            idUserDestinatario =" ${userdestinatario.nome.replace(" ","")}${userdestinatario.email.replace("@","").replace(".","").trim()}"

        }

    }
    private fun setupList() {
        initToolBar()
        val adapter = initAdapter()
        initRecyclerView(adapter)
    }

    @SuppressLint("ResourceType")
    private fun initToolBar() {
        val toolbar = binding.toolbarChat
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun initAdapter() : MensagensAdapter{
        mensagensAdapter = MensagensAdapter(this, listaMensagens)
        return mensagensAdapter
    }
    fun initRecyclerView(adapter: MensagensAdapter) {
        binding.containerChat.rvConversasChat.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }
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
    private fun enviarMensagem(){
        val txtMensagem = binding.containerChat.editTextMensagemChat.text.toString()
        if(!txtMensagem.isEmpty()){
            val mensagem = Mensagem()
            mensagem.idUsuario = userKey()
            mensagem.mensagem = txtMensagem
            mensagem.dataEHora = dataEHoraAtual()
            salvarMensagem(userKey(),idUserDestinatario,mensagem)
        }
    }
    private fun salvarMensagem(idRemetente : String,idDestinatario : String,mensagem : Mensagem){
        RealtimeDBFirebase.salvarMensagemChat(idRemetente,idDestinatario,mensagem)
        binding.containerChat.editTextMensagemChat.text.clear()
    }

    override fun onStart() {
        super.onStart()
       viewModel.carregarMsgs(idUserDestinatario)


    }

    private fun notifyAdapter(it: Mensagem) {
        listaMensagens.add(it)
        mensagensAdapter.notifyDataSetChanged()
    }

    private fun observers() {
        viewModel.mensagem.observe(this, Observer { state->
            when(state){
                is StateMessage.Success->{
                    binding.chatProgressBar.hide()
                    binding.containerChat.root.show()
                    state.messageValue?.let { listaMensagens.add(it) }
                    mensagensAdapter.notifyDataSetChanged()
                }
                is StateMessage.Loading->{
                    binding.containerChat.root.hide()
                    binding.chatProgressBar.show()
                }
                is StateMessage.Error->{
                    binding.chatProgressBar.hide()
                    binding.containerChat.root.show()
                    state.messageError?.let { message(it) }
                }
                else->{}
            }
        })
    }

}