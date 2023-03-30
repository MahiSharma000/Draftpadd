package com.example.draftpad

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.draftpad.auth.SharedPreferenceManager
import com.example.draftpad.databinding.ActivityMainBinding
import com.example.draftpad.setting.SettingsFragment
import com.example.draftpad.ui.home.HomeFragment
import com.example.draftpad.ui.notifications.NotificationsFragment
import com.example.draftpad.ui.premium.PremiumFragment
import com.example.draftpad.ui.profile.UserProfileFragment
import com.example.draftpad.ui.write.WriteFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferencesManager: SharedPreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesManager = SharedPreferenceManager(this)
        if(!sharedPreferencesManager.isLoggedIn()){
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_write, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // hide action bar
        try {
            this.supportActionBar?.hide()
        } catch (e: NullPointerException) {
        }
    }
}