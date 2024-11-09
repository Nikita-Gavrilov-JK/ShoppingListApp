package com.example.shoppinglistapp.view.activites

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.MainApp
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityShopListBinding
import com.example.shoppinglistapp.model.ShopListItem
import com.example.shoppinglistapp.model.ShopListNameItem
import com.example.shoppinglistapp.model.database.ShopListItemAdapter
import com.example.shoppinglistapp.view.dialog.EditListItemDialog
import com.example.shoppinglistapp.viewmodel.ShoppingListViewModel

class ShopListActivity : AppCompatActivity(), ShopListItemAdapter.Listener {
    private lateinit var binding: ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null
    private var adapter: ShopListItemAdapter? = null
    // Этот класс уже является Активити поэтому мы сразу пишем viewModels
    private val shoppingListViewModel: ShoppingListViewModel by viewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory((applicationContext as MainApp).database)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initRcView()
        listItemObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item_list)!!
        val newItem = menu.findItem(R.id.new_item_list)
        edItem = newItem.actionView?.findViewById(R.id.edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_item_list){
            addNewShopItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewShopItem() {
        if (edItem?.text.toString().isEmpty()) return
        val item = ShopListItem(
            null,
            edItem?.text.toString(),
            null,
            false,
            shopListNameItem?.id!!,
            0
        )
        edItem?.setText("")
        shoppingListViewModel.insertShopItem(item)
    }

    private fun listItemObserver(){
        shoppingListViewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this, {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()){
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun initRcView()= with(binding) {
        adapter = ShopListItemAdapter(this@ShopListActivity)
        rcView.layoutManager = LinearLayoutManager(this@ShopListActivity)
        rcView.adapter = adapter
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
    }

    //companion object используем для добавления констант
    companion object{
        const val SHOP_LIST_NAME = "shop_list_name"
    }

    override fun onClickItem(shopListItem: ShopListItem, state: Int) {
        when(state) {
            ShopListItemAdapter.CHECK_BOX -> shoppingListViewModel.updateItem(shopListItem)
            ShopListItemAdapter.EDIT -> editListitem(shopListItem)
        }
    }
    private fun editListitem(item: ShopListItem) {
        EditListItemDialog.showDialog(this, item, object : EditListItemDialog.Listener{
            override fun onClick(item: ShopListItem) {
                shoppingListViewModel.updateItem(item)
            }
        })
    }
}