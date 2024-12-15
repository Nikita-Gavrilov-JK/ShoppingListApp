package com.example.shoppinglistapp.view.activites

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.view.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    private lateinit var defPref: SharedPreferences

    //Фрагмент SettingsFragment запустится из этого активити
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            // Мы заменяем content activity_settings и передаём в него фрагмент
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedTheme(): Int {
        return if (defPref.getString("theme_key", "Светлая") == "Светлая") {
            R.style.Theme_ShoppingListAppWhite
        } else {
            R.style.Theme_ShoppingListAppPurple
        }
    }
}