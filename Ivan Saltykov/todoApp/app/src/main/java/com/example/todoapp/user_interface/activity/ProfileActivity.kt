package com.example.todoapp.user_interface.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityProfileBinding
import com.example.todoapp.preferences.PreferencesManager
import com.example.todoapp.user_interface.fragment.ProfileFragment
import com.example.todoapp.user_interface.fragment.TasksFragment

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var preferences: PreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = PreferencesManager(this)
        setSupportActionBar(binding.toolbarProfile)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.navigationView.setNavigationItemSelectedListener { menuitem ->
            when (menuitem.itemId) {
                R.id.menuFragmentprofile -> openFragment(ProfileFragment())
                R.id.menuFragmenttasks -> openFragment(TasksFragment())
            }
            return@setNavigationItemSelectedListener true
        }
        val toogle = ActionBarDrawerToggle(
            this,
            binding.drawerLayoutProfile,
            binding.toolbarProfile,
            R.string.nav_open_drawer,
            R.string.nav_close_drawer
        )
        binding.drawerLayoutProfile.addDrawerListener(toogle)
        toogle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemToolbarProfileExit -> {
                preferences.clearPreferences()
                finish()
            }
        }
        return true
    }
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentcontainerprofile, fragment)
            .commit()
        binding.drawerLayoutProfile.closeDrawer(GravityCompat.START)
    }
}
