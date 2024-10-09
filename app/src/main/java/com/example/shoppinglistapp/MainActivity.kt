package com.example.shoppinglistapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.example.shoppinglistapp.view.FragmentManager
import com.example.shoppinglistapp.view.NoteFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.botNavMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> { Log.d("MYLOG", "Settings")}
                R.id.notes -> {
                   FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {Log.d("MYLOG", "Shop_list")}
                R.id.new_item -> {Log.d("MYLOG", "New_item")}
            }
            true
        }
    }
}