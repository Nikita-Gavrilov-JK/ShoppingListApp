package com.example.shoppinglistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.example.shoppinglistapp.view.FragmentManager
import com.example.shoppinglistapp.view.NoteFragment
import com.example.shoppinglistapp.view.ShopListNamesFragment
import com.example.shoppinglistapp.view.activites.SettingsActivity
import com.example.shoppinglistapp.view.dialog.NewListDialog

class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
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
                   FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)}
                R.id.new_item -> {
                    FragmentManager.currentFragment?.onClickNew()
                }
            }
            true
        }
    }

    override fun onClick(name: String) {

    }
}