package com.example.shoppinglistapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.example.shoppinglistapp.view.FragmentManager
import com.example.shoppinglistapp.view.NoteFragment
import com.example.shoppinglistapp.view.ShopListNamesFragment
import com.example.shoppinglistapp.view.activites.SettingsActivity
import com.example.shoppinglistapp.view.dialog.NewListDialog

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.shop_list
    private lateinit var defPref: SharedPreferences
    private var currentTheme = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = defPref.getString("theme_key", "Светлая").toString()
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.botNavMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.notes -> {
                   currentMenuItemId = R.id.notes
                   FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {
                    currentMenuItemId = R.id.shop_list
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)}
                R.id.new_item -> {
                    FragmentManager.currentFragment?.onClickNew()
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.botNavMenu.selectedItemId = currentMenuItemId
        //Делаем проверку на пересоздание активити
        if (defPref.getString("theme_key", "Светлая") != currentTheme) recreate()
    }
    private fun getSelectedTheme(): Int {
        return if (defPref.getString("theme_key", "Светлая") == "Светлая") {
            R.style.Theme_ShoppingListAppWhite
        } else {
            R.style.Theme_ShoppingListAppPurple
        }
    }

    override fun onClick(name: String) {

    }
}