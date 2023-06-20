package com.example.cropdiary.ui.view.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.cropdiary.R
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.databinding.ActivityHomeBinding
import com.example.cropdiary.ui.view.Auth.AuthActivity
import com.example.cropdiary.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var email: String
    private lateinit var provider: String
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_crops, R.id.nav_workers, R.id.nav_slideshow
            ), drawerLayout
        )
        setup()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun setup(){
        val prefs = SharedPrefUserHelper.getUserPrefs(this)
        this.email = prefs.email.toString()
        this.provider = prefs.provider.toString()
        authViewModel.authResultModel.observe(this) {
            SharedPrefUserHelper.clearPrefs(this)
            if (it.isSuccess)
                startActivity(Intent(this, AuthActivity::class.java))
        }
    }

    private fun signOut() {
        authViewModel.signOut(
            ProviderType.valueOf(provider),
            googleSignInClient
        )
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home_drawer, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        findViewById<Button>(R.id.btnLogOutDrawer).setOnClickListener {signOut()}
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}