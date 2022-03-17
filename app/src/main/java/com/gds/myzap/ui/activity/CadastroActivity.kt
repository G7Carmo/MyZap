package com.gds.myzap.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gds.myzap.databinding.ActivityCadastroBinding
import com.gds.myzap.firebase.auth.AuthFirebase
import com.gds.myzap.firebase.db.RealtimeDatabaseFirebase
import com.gds.myzap.model.Usuario
import com.gds.myzap.util.dialog
import com.gds.myzap.util.message
import com.gds.myzap.util.nextScreen
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private val auth by lazy { AuthFirebase }
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

    private fun cadastrarUsuario(user: Usuario) = lifecycleScope.launch{
        auth.createUserFirebase(user,this@CadastroActivity){
            result(it,user)
        }
    }

    private fun result(it: Task<AuthResult>,user: Usuario) {
        if (it.isSuccessful){
            dialog("Sucesso","Cadastro realizado com sucesso!")
            try {
                salvandoNoDbRemoto(user)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }else{
            dialog("Falha","Falha ao cadastrar")
        }
    }

    private fun salvandoNoDbRemoto(user: Usuario) = lifecycleScope.launch{
        RealtimeDatabaseFirebase.salvarDadosDoCadastro(user)
    }

    private fun criarUsuario(nome: String, email: String, senha: String) : Usuario {
        return Usuario(nome,email,senha)
    }
}