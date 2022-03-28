package com.gds.myzap.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gds.myzap.R
import com.gds.myzap.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolBar()
        listners()
    }


    private fun initToolBar() {
        val toolbar = binding.toolbarChat.toolbar
        toolbar.title = "MyZap"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun listners() = with(binding) {
        fab.setOnClickListener {

        }
   }
}