package com.masmuzi.finalcoderstoryapps.ui.mainmenu

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.masmuzi.finalcoderstoryapps.R
import com.masmuzi.finalcoderstoryapps.adapter.AdapterListStory
import com.masmuzi.finalcoderstoryapps.adapter.LoadingStateAdapter
import com.masmuzi.finalcoderstoryapps.databinding.ActivityMainBinding
import com.masmuzi.finalcoderstoryapps.ui.login.LoginActivity
import com.masmuzi.finalcoderstoryapps.ui.story.StoryActivity
import com.masmuzi.finalcoderstoryapps.ui.factory.FactoryStoryVM
import com.masmuzi.finalcoderstoryapps.ui.maps.MapsActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVM: MainVM
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStories.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStories.layoutManager = LinearLayoutManager(this)
        }

        title = getString(R.string.app_name)
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        val factory: FactoryStoryVM = FactoryStoryVM.getInstance(this)
        mainVM = ViewModelProvider(
            this,
            factory
        )[MainVM::class.java]

        mainVM.isLogin().observe(this){
            if (!it){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        mainVM.getToken().observe(this){ token ->
            this.token = token
            if (token.isNotEmpty()){
                val adapter = AdapterListStory()
                binding.rvStories.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    }
                )
                mainVM.getStories(token).observe(this){ result ->
                    adapter.submitData(lifecycle, result)
                }
            }
        }

        mainVM.getName().observe(this) {name ->
            binding.tvWelcomeName.text = name
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.items_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                mainVM.logout()
                true
            }
            R.id.map_menu -> {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra(MapsActivity.EXTRA_TOKEN, token)
                startActivity(intent)
                true
            }
            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }

    private fun setupAction() {
        binding.fabAddStories.setOnClickListener {
            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra(StoryActivity.EXTRA_TOKEN, token)
            startActivity(intent)
        }
    }
}