package com.gds.myzap.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.gds.myzap.R
import com.gds.myzap.databinding.ActivityMainBinding
import com.gds.myzap.firebase.auth.AuthFirebase
import com.gds.myzap.ui.fragments.ContatosFragment
import com.gds.myzap.ui.fragments.ConversasFragment
import com.gds.myzap.util.nextScreen
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolBar()
        initAbas()
    }

    private fun initAbas() {
        val adapterViewpager = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(this)
                .add("Conversas",ConversasFragment::class.java)
                .add("Contatos",ContatosFragment::class.java)
            .create()
        )
        val viewPager = binding.viewpager
        viewPager.adapter = adapterViewpager

        val smartTabLayout = binding.viewpagertab
        smartTabLayout.setViewPager(viewPager)
    }

    @SuppressLint("ResourceAsColor")
    private fun initToolBar() {
        val toolbar = binding.toolbarMain.toolbar
        toolbar.title = "MyZap"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuSair->{
                deslogar()
                finish()
            }
            R.id.menuConfig->{
                nextScreen(ConfiguracaoActivity())
            }
            R.id.menuPesquisa->{

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deslogar() = lifecycleScope.launch {
        AuthFirebase.logout()
    }

}