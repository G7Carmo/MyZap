package com.gds.myzap.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gds.myzap.databinding.FragmentContatosBinding
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.ui.view.activity.ChatActivity
import com.gds.myzap.ui.view.adapter.ContatosAdapter
import com.gds.myzap.ui.viewmodel.fragment.ContatosViewModel
import com.gds.myzap.util.RecyclerItemClickListner
import com.gds.myzap.util.message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class ContatosFragment : Fragment() {
    private lateinit var binding: FragmentContatosBinding
    private val listContatos: ArrayList<Usuario> = arrayListOf()
    private lateinit var adapterContatos: ContatosAdapter
    private val viewModel: ContatosViewModel by viewModels()
    private lateinit var usuarioAtual: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usuarioAtual = UsuarioFirebase.dadosUsuarioLogado()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContatosBinding.inflate(layoutInflater)
        initRecyclerView()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.recuperarDados(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val user = it.getValue(Usuario::class.java)!!
                    if (!user.email.equals(usuarioAtual.email)) {
                        listContatos.add(user)
                    }
                }
                adapterContatos.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                message(error.message)
            }

        }
        )
    }

    private fun initRecyclerView() = with(binding) {
        adapterContatos = ContatosAdapter(listContatos, requireContext())
        recyclerViewContatos.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterContatos
            addOnItemTouchListener(getListner(this))
        }
    }

    private fun getListner(recyclerView: RecyclerView): RecyclerView.OnItemTouchListener {
        return RecyclerItemClickListner(context,recyclerView,object : RecyclerItemClickListner.OnItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                Intent(context,ChatActivity::class.java).apply {
                    startActivity(this)
                }
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onLongItemClick(view: View?, position: Int) {
                TODO("Not yet implemented")
            }

        })
    }

}