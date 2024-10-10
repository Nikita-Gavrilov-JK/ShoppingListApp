package com.example.shoppinglistapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.database.ShoppingListDB
import kotlinx.coroutines.launch

class ShoppingListViewModel(database: ShoppingListDB):ViewModel() {
    val dao = database.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
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