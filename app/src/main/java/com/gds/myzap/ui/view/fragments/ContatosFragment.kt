package com.gds.myzap.ui.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gds.myzap.databinding.FragmentContatosBinding
import com.gds.myzap.data.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.ui.view.adapter.ContatosAdapter
import com.gds.myzap.ui.viewmodel.fragment.ContatosViewModel
import com.gds.myzap.util.hide
import com.gds.myzap.util.message
import com.gds.myzap.util.show
import com.gds.myzap.util.state.UserState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch


class ContatosFragment : Fragment() {
    private lateinit var binding: FragmentContatosBinding
    private val listContatos: ArrayList<Usuario> = arrayListOf()
    private lateinit var adapterContatos: ContatosAdapter
    private val viewModel : ContatosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel.recuperarDados(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val value = it.getValue(Usuario::class.java)!!
                    listContatos.add(value)
                }
                adapterContatos.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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
        }
    }

}