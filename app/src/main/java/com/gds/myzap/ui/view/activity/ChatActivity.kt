package com.gds.myzap.ui.view.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.data.firebase.RealtimeDBFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase.userKey
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.databinding.ActivityChatBinding
import com.gds.myzap.ui.view.adapter.MensagensAdapter
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    private lateinit var userdestinatario : Usuario
    private lateinit var idUserDestinatario : String
    private lateinit var mensagensAdapter : MensagensAdapter
    private var listaMensagens : ArrayList<Mensagem> = arrayListOf()


    val childEventListener = object : ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val mensagem = snapshot.getValue(Mensagem::class.java)
            mensagem?.let { listaMensagens.add(it) }
            mensagensAdapter.notifyDataSetChanged()
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundleValeus()
        setupList()
        listners()
    }

    override fun onStart() {
        super.onStart()
        recuperarMensagens()
    }

    override fun onStop() {
        super.onStop()
        RealtimeDBFirebase.stopEventListner(childEventListener)
    }

    private fun setupList() {
        initToolBar()
        val adapter = initAdapter()
        initRecyclerView(adapter)
    }

    private fun initAdapter() : MensagensAdapter{
        mensagensAdapter = MensagensAdapter(this, listaMensagens)
        return mensagensAdapter
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
            idUserDestinatario = userdestinatario.nome  + userdestinatario.email.replace("@","").replace(".","")

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
    fun initRecyclerView(adapter: MensagensAdapter) {
        binding.containerChat.rvConversasChat.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
        }
    }
    private fun enviarMensagem(){
        val txtMensagem = binding.containerChat.editTextMensagemChat.text.toString()
        if(!txtMensagem.isEmpty()){
            val mensagem = Mensagem()
            mensagem.idUsuario = userKey()
            mensagem.mensagem = txtMensagem
            salvarMensagem(userKey(),idUserDestinatario,mensagem)
        }
    }
    private fun salvarMensagem(idRemetente : String,idDestinatario : String,mensagem : Mensagem){
        RealtimeDBFirebase.salvarMensagemChat(idRemetente,idDestinatario,mensagem)
        binding.containerChat.editTextMensagemChat.text.clear()
    }
    fun recuperarMensagens(){
        RealtimeDBFirebase.recuperarMensagenParaoChat(userKey(),idUserDestinatario,childEventListener)
    }

}