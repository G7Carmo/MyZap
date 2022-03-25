package com.gds.myzap.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gds.myzap.databinding.FragmentContatosBinding
import com.gds.myzap.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.model.Usuario
import com.gds.myzap.ui.adapter.ContatosAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class ContatosFragment : Fragment() {
    private lateinit var binding: FragmentContatosBinding
    private val listContatos: ArrayList<Usuario> = arrayListOf()
    private lateinit var adapterContatos: ContatosAdapter
    private lateinit var valueEventListener: ValueEventListener
    private lateinit var dbRefUser: DatabaseReference


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
        recuperandoContatos()
    }

    override fun onStop() {
        super.onStop()
        dbRefUser.removeEventListener(valueEventListener)
    }

    private fun initRecyclerView() = with(binding) {
        adapterContatos = configAdapter()
        recyclerViewContatos.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterContatos
        }
    }

    private fun configAdapter(): ContatosAdapter {
        listContatos.map { user ->
            user?.let {

            }
        }
        return ContatosAdapter(listContatos, requireContext())
    }

    fun recuperandoContatos() {
        val dbRefUser = RealtimeDatabaseFirebase.db.child("Usuarios")
        valueEventListener = dbRefUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { data ->
                    val nome = snapshot.child("nome").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)
                    val foto = snapshot.child("foto").getValue(String::class.java)
                    nome?.let { nomeSafe->
                        email?.let { emailSafe->
                            foto?.let { fotoSafe->
                                val userSafe = generateUserSafe(nomeSafe, emailSafe, fotoSafe)
                                listContatos.add(userSafe)
                            }
                        }
                    }
                }
                notificarAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun generateUserSafe(
        nomeSafe: String,
        emailSafe: String,
        fotoSafe: String
    ): Usuario {
        val user = Usuario.Builder
        user.nome(nomeSafe)
        user.email(emailSafe)
        user.foto(fotoSafe)
        return user.builder()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notificarAdapter() = adapterContatos.notifyDataSetChanged()


}