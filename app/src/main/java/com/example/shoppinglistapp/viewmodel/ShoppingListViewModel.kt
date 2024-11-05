package com.example.shoppinglistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.ShopListItem
import com.example.shoppinglistapp.model.ShopListNameItem
import com.example.shoppinglistapp.model.database.ShoppingListDB
import kotlinx.coroutines.launch

class ShoppingListViewModel(database: ShoppingListDB):ViewModel() {
    val dao = database.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames: LiveData<List<ShopListNameItem>> = dao.getAllShopListNames().asLiveData()
    fun getAllItemsFromList(listid: Int): LiveData<List<ShopListItem>> {
        return dao.getAllShopListItems(listid).asLiveData()
    }
    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertShopListName(listName: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }

    fun insertShopItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun deleteShopListName(id: Int) = viewModelScope.launch {
        dao.deleteShopListName(id)
    }

    fun updateShopListName(shopListNameItem: ShopListNameItem) = viewModelScope.launch {
        dao.updateShopListName(shopListNameItem)
    }

    class ShoppingListViewModelFactory(val database: ShoppingListDB): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ShoppingListViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}