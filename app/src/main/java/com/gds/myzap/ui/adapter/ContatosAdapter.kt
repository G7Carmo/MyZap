package com.gds.myzap.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gds.myzap.R
import com.gds.myzap.databinding.FragmentContatosBinding
import com.gds.myzap.databinding.ItemListBinding
import com.gds.myzap.model.Usuario

class ContatosAdapter(
    private val listContatos: ArrayList<Usuario>,
    private val context: Context
) : RecyclerView.Adapter<ContatosAdapter.ContatosViewHolder>() {
    inner class ContatosViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatosViewHolder {
        return ContatosViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ContatosViewHolder, position: Int) {
        val usuario = listContatos[position]
        holder.binding.apply {
            nomeItem.text = usuario.nome
            emialItem.text = usuario.email
            usuario.foto.isNotEmpty().apply {
                val uri = Uri.parse(usuario.foto)
                Glide.with(context).load(uri).into(imageViewItem)
            }.let {
                imageViewItem.setImageResource(R.drawable.padrao)
            }
        }
    }

    override fun getItemCount() = listContatos.size
}