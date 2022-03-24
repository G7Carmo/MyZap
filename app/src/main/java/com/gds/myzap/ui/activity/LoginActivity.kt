package com.gds.myzap.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.gds.myzap.databinding.ActivityLoginBinding
import com.gds.myzap.firebase.AuthFirebase
import com.gds.myzap.firebase.UsuarioFirebase
import com.gds.myzap.model.Usuario
import com.gds.myzap.util.dialog
import com.gds.myzap.util.message
import com.gds.myzap.util.nextScreen
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val auth : AuthFirebase = AuthFirebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListners()
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
        validarDados(email,senha)
    }

    private fun validarDados(email: String, senha: String) {
        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                logarUsuario(gerarUsuario(email,senha))
            }else{
                message("Campo senha vazio")
                binding.textSenhaLabelLayout.error = "Obrigatorio!"
            }
        }else{
            message("Campo email vazio")
            binding.textEmailLabelLayout.error = "Obrigatorio!"
        }
    }

    private fun gerarUsuario(email: String, senha: String): Usuario {
        return Usuario(
            "",email,senha,""
        )
    }

    private fun logarUsuario(usuario: Usuario) = lifecycleScope.launch{
        auth.login(usuario,this@LoginActivity){
            result(it)
        }
    }

    override fun onStart() {
        validaUserLogado()
        super.onStart()
    }

    private fun validaUserLogado() = lifecycleScope.launch {
        if(UsuarioFirebase.verifyCurrentUser()){
            nextScreen(MainActivity())
        }
    }

    private fun result(it: Task<AuthResult>) {
        if (it.isSuccessful){
            nextScreen(MainActivity())
        }else{
            dialog("Falha","Falha ao cadastrar")
        }
    }

}