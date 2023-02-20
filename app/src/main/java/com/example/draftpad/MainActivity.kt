package com.example.draftpad

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
import com.example.draftpad.databinding.ActivityMainBinding
import com.example.draftpad.setting.SettingFragment
import com.example.draftpad.ui.home.HomeFragment
import com.example.draftpad.ui.notifications.NotificationsFragment
import com.example.draftpad.ui.premium.PremiumFragment
import com.example.draftpad.ui.profile.UserProfileFragment
import com.example.draftpad.ui.write.WriteFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle("Draftpad")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navvView : NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navvView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, UserProfileFragment() ).commit()
                    true
                }
                R.id.nav_premium -> {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, PremiumFragment()).commit()
                    true
                }
                R.id.nav_settings-> {
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, SettingFragment()).commit()
                    true
                }
                else -> false
            }
        }

        fun onOptionsItemSelected(item: MenuItem): Boolean {
            if(toggle.onOptionsItemSelected(item)){
                return true
            }
            return super.onOptionsItemSelected(item)
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
    }
}