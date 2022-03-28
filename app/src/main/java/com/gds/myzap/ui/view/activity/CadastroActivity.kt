package com.gds.myzap.ui.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.gds.myzap.databinding.ActivityCadastroBinding
import com.gds.myzap.data.firebase.RealtimeDatabaseFirebase
import com.gds.myzap.data.firebase.UsuarioFirebase
import com.gds.myzap.data.model.Usuario
import com.gds.myzap.ui.viewmodel.activity.CadastroViewModel
import com.gds.myzap.util.*
import com.gds.myzap.util.state.UserState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private val viewModel : CadastroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListners()
    }
    private fun initListners() = with(binding) {
        textLogarCadastro.setOnClickListener {
            nextScreen(LoginActivity())
            finish()
        }
        btnCadastrar.setOnClickListener {
            initViews()
        }
    }

    private fun initViews() = with(binding) {
        val nome = editNomeCadastro.text.toString()
        val email = editEmailCadastro.text.toString()
        val senha = editSenhaCadastro.text.toString()
        validarDados(nome,email,senha)
    }

    private fun validarDados(nome: String, email: String, senha: String) {
        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                if (!nome.isEmpty()){
                    val usuarioValido = criarUsuario(nome, email, senha)
                    cadastrarUsuario(usuarioValido)
                }else{
                    message("Campo nome vazio")
                    binding.textNomeLabelCadastro.error = "Obrigatorio!"
                }
            }else{
                message("Campo senha vazio")
                binding.textSenhaLabelCadastro.error = "Obrigatorio!"
            }
        }else{
            message("Campo email vazio")
            binding.textEmailLabelCadastro.error = "Obrigatorio!"
        }
    }
    private fun criarUsuario(nome: String, email: String, senha: String) : Usuario {
        return Usuario(nome,email,senha,"")
    }

    private fun cadastrarUsuario(user: Usuario){
        viewModel.createUserFirebase(user,this@CadastroActivity)
        observes(user)
    }

    private fun observes(user: Usuario) {
        viewModel.createUser.observe(this, Observer { state->
            when(state){
                is UserState.Success->{
                    binding.progressBarCadastro.hide()
                    viewModel.salvandoNoDbRemoto(user)
                    viewModel.salvandoNomeUser(user.nome)
                    nextScreen(MainActivity())
                }
                is UserState.Error->{
                    binding.progressBarCadastro.hide()
                    dialog("Falha","Falha ao castrar")
                }
                is UserState.Loading->{
                    binding.progressBarCadastro.show()
                }
                else->{}
            }
        })
    }


}