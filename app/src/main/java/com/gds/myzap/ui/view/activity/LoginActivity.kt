package com.gds.myzap.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.gds.myzap.databinding.ActivityLoginBinding
import com.gds.myzap.data.firebase.AuthFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.ui.viewmodel.activity.LoginViewModel
import com.gds.myzap.util.*
import com.gds.myzap.util.state.UserState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModels: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListners()
        observes()
    }

    private fun initListners() = with(binding) {
        textCadastreseLogin.setOnClickListener {
            nextScreen(CadastroActivity())
        }
        btnEntrarLogin.setOnClickListener {
            initViews()
        }
    }

    private fun initViews() = with(binding) {
        val email = editEmailLogin.text.toString()
        val senha = editSenhaLogin.text.toString()
        validarDados(email, senha)
    }

    private fun validarDados(email: String, senha: String) {
        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                logarUsuario(gerarUsuario(email, senha))
            } else {
                message("Campo senha vazio")
                binding.textSenhaLabelLayout.error = "Obrigatorio!"
            }
        } else {
            message("Campo email vazio")
            binding.textEmailLabelLayout.error = "Obrigatorio!"
        }
    }

    private fun logarUsuario(usuario: Usuario) = lifecycleScope.launch {
        viewModels.login(usuario, this@LoginActivity)
    }

    private fun gerarUsuario(email: String, senha: String): Usuario {
        return Usuario(
            "", email, senha, ""
        )
    }


    override fun onStart() {
        validaUserLogado()
        super.onStart()
    }

    private fun validaUserLogado() = lifecycleScope.launch {
        if (UsuarioFirebase.verifyCurrentUser()) {
            nextScreen(MainActivity())
        }
    }

    private fun observes() {
        viewModels.login.observe(this, Observer { state->
            when(state){
                is UserState.Success->{
                    binding.progessBarLogin.hide()
                    nextScreen(MainActivity())
                }
                is UserState.Error->{
                    binding.progessBarLogin.hide()
                    dialog("Falha","Falha no Login")
                }
                is UserState.Loading->{
                    binding.progessBarLogin.show()
                }
                else->{}
            }

        })
    }
}