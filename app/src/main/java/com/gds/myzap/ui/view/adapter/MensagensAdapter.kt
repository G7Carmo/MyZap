package com.gds.myzap.ui.view.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Mensagem
import com.gds.myzap.databinding.AdapterMensagemRemetenteBinding
import com.gds.myzap.util.hide

class MensagensAdapter(
    private val context: Context,
    private val listMensagens : List<Mensagem>
) : RecyclerView.Adapter<MensagensAdapter.MensagensViewHolder>() {
    companion object{
        const val REMETENTE = 0
        const val DESTINATARIO = 1
    }
    inner class MensagensViewHolder(val binding: AdapterMensagemRemetenteBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagensViewHolder {
        if (viewType == REMETENTE){
            return MensagensViewHolder(
                AdapterMensagemRemetenteBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false
                )
            )
        }
        return MensagensViewHolder(
            AdapterMensagemRemetenteBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MensagensViewHolder, position: Int) {
        val mensagem = listMensagens[position]
        holder.binding.apply {
            textMensagemTexto.text = mensagem.mensagem
            if (!mensagem.foto.isEmpty()){
                val uri = Uri.parse(mensagem.foto)
                Glide.with(context).load(uri).into(imageMensagemFoto)

                textMensagemTexto.hide()
            }else{
                textMensagemTexto.text = mensagem.mensagem
                imageMensagemFoto.hide()
            }

        }

    }

    override fun getItemCount(): Int = listMensagens.size

    override fun getItemViewType(position: Int): Int {
        val mensagem = listMensagens[position]
        val idUserAtual = UsuarioFirebase.userKey()
        if (idUserAtual == mensagem.idUsuario){
            return REMETENTE
        }
        return DESTINATARIO
    }
}
