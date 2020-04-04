package com.sarath.dev.covid

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import java.net.URL


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

    private fun setUpAboutDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        val viewGroup = findViewById<ViewGroup>(R.id.content)
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.layout_about_page, viewGroup, false)
        builder.setView(dialogView)
        setUpDialogViews(dialogView)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun setUpDialogViews(root: View) {
        val developedByTextView = root.findViewById<TextView>(R.id.developed_by)
        val apiBy = root.findViewById<TextView>(R.id.api_data_from)
        val newsFrom = root.findViewById<TextView>(R.id.news_data_from)

        setDevelopedBySpannable(developedByTextView, "Developed by: Sarath Sattiraju, Mohan Krishna Kosetty")
        setApiBySpannable(apiBy, "Api From: Postman")
        setNewsBySpannable(newsFrom, "News from: News API")
    }

    private fun setApiBySpannable(view: View, total: String) {
        val spannableString = SpannableString(total)

        val postmanIndex = total.indexOf("Postman")
        spannableString.setSpan(URLSpan("https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=2ahUKEwiUwaTNiM7oAhVHwzgGHfw4DAkQFjAAegQIAhAB&url=https%3A%2F%2Fcovid-19-apis.postman.com%2F&usg=AOvVaw1n9bHnBzZFWh8ut1NGyVz9"),
                                    postmanIndex, postmanIndex + "Postman".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                navigateToUrl("https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=2ahUKEwiUwaTNiM7oAhVHwzgGHfw4DAkQFjAAegQIAhAB&url=https%3A%2F%2Fcovid-19-apis.postman.com%2F&usg=AOvVaw1n9bHnBzZFWh8ut1NGyVz9")
            }

        }, postmanIndex, postmanIndex + "Postman".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        (view as TextView).text = spannableString
    }

    private fun setNewsBySpannable(view: View, total: String) {
        val spannableString = SpannableString(total)

        val newsApiOrg = total.indexOf("News API")
        spannableString.setSpan(URLSpan("https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=2ahUKEwjYkIaBic7oAhV_yTgGHXvdA-sQFjAAegQIBxAC&url=https%3A%2F%2Fnewsapi.org%2F&usg=AOvVaw1EzrrF35KRt_5CLONmtNet"),
                                newsApiOrg, newsApiOrg + "News API".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                navigateToUrl("https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=2ahUKEwjYkIaBic7oAhV_yTgGHXvdA-sQFjAAegQIBxAC&url=https%3A%2F%2Fnewsapi.org%2F&usg=AOvVaw1EzrrF35KRt_5CLONmtNet")
            }

        }, newsApiOrg, newsApiOrg + "News API".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        (view as TextView).text = spannableString
    }

    private fun setDevelopedBySpannable(view: View, total: String) {
        val spannableString = SpannableString(total)

        val sarathNameIndex = total.indexOf("Sarath Sattiraju")
        val mohanNameIndex = total.indexOf("Mohan Krishna Kosetty")
        spannableString.setSpan(URLSpan("https://www.linkedin.com/in/sarath-sattiraju/"), sarathNameIndex, sarathNameIndex + "Sarath Sattiraju".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                navigateToUrl("https://www.linkedin.com/in/sarath-sattiraju/")
            }

        }, sarathNameIndex, sarathNameIndex + "Sarath Sattiraju".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(URLSpan("https://www.linkedin.com/in/mohan-krishna-888689b7/"), mohanNameIndex, mohanNameIndex + "Mohan Krishna Kosetty".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                navigateToUrl("https://www.linkedin.com/in/mohan-krishna-888689b7/")
            }

        }, mohanNameIndex, mohanNameIndex + "Mohan Krishna Kosetty".length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        (view as TextView).text = spannableString
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
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
            setUpAboutDialog()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        COVID19.context = this
    }
}
