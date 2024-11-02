package com.example.shoppinglistapp.view.activites

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglistapp.MainApp
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityShopListBinding
import com.example.shoppinglistapp.model.ShopListNameItem
import com.example.shoppinglistapp.viewmodel.ShoppingListViewModel

class ShopListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    // Этот класс уже является Активити поэтому мы сразу пишем viewModels
    private val shoppingListViewModel: ShoppingListViewModel by viewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory((applicationContext as MainApp).database)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item_list)!!
        val newItem = menu.findItem(R.id.new_item_list)
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        return true
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
                return true
            }

        }
    }

    //В этой функции(init) будем инициализировать RecyclerView и будем получать из Intent какой список мы открыли
    private fun init(){
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem
        binding.tvTest.text = shopListNameItem?.name
    }

    //companion object используем для добавления констант
    companion object{
        const val SHOP_LIST_NAME = "shop_list_name"
    }
}