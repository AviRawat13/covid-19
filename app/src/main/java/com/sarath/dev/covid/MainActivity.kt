package com.sarath.dev.covid

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sarath.dev.covid.controllers.network.country.CountryMapper
import com.sarath.dev.covid.controllers.utils.Constants
import com.sarath.dev.covid.controllers.utils.ToastsUtil

class MainActivity : AppCompatActivity() {
    private var requested = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        COVID19.context = this
        setContentView(R.layout.activity_main)
        requestPermissions()
        populateCountries()
    }

    private fun populateCountries() {
        CountryMapper().fetchCountries()
    }

    private fun initialSetUp() {
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

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(
                COVID19.context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (requested)
                ToastsUtil.l(getString(R.string.permissions_required), this)

            requested = true
            ActivityCompat.requestPermissions(COVID19.context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_CODE_LOCATION)
        } else {
            initialSetUp()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.REQUEST_CODE_LOCATION) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                initialSetUp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        COVID19.context = this
    }
}
