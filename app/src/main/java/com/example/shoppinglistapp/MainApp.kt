package com.example.shoppinglistapp

import android.app.Application
import com.example.shoppinglistapp.model.database.ShoppingListDB

class MainApp: Application() {
    val database by lazy { ShoppingListDB.getDataBase(this) }
}