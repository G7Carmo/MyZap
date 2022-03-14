package com.gds.myzap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gds.myzap.databinding.ActivityCadastroBinding
import com.gds.myzap.util.nextScreen

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
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
    }
}