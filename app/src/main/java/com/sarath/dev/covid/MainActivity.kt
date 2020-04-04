package com.sarath.dev.covid

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        setActionBar(false)
    }

    private fun setUpCallback(navView: BottomNavigationView, navController: NavController) {
        navView.setOnNavigationItemSelectedListener{
                menuItem ->
            if (menuItem.itemId == R.id.navigation_world) {
                setActionBar(false)
                navController.navigate(R.id.navigation_world)
            } else if (menuItem.itemId == R.id.navigation_local) {
                setActionBar(true)
                navController.navigate(R.id.navigation_local)
            }

            true
        }
    }

    private fun setActionBar(isLocal: Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (isLocal) {
            supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#311b92")))
            window.statusBarColor = Color.parseColor("#000063")
        } else {
            supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#e57373")))
            window.statusBarColor = Color.parseColor("#af4448")
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.about) {
            ToastsUtil.d("Here")
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        COVID19.context = this
    }
}
