package com.sarath.dev.covid

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sarath.dev.covid.controllers.utils.ToastsUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_world, R.id.navigation_local
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setUpCallback(navView, navController)
    }

    private fun setUpCallback(navView: BottomNavigationView, navController: NavController) {
        navView.setOnNavigationItemSelectedListener{
            menuItem ->
            if (menuItem.itemId == R.id.navigation_world) {
                navController.navigate(R.id.navigation_world)
            } else if (menuItem.itemId == R.id.navigation_local) {
                navController.navigate(R.id.navigation_local)
            }

            true
        }
    }
}
