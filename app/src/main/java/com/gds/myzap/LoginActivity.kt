package com.gds.myzap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gds.myzap.databinding.ActivityLoginBinding
import com.gds.myzap.util.nextScreen

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListners()
    }

    private fun initListners() = with(binding) {
        textCadastreseLogin.setOnClickListener {
            nextScreen(CadastroActivity())
            finish()
        }
        btnEntrarLogin.setOnClickListener {

        }
    }
}