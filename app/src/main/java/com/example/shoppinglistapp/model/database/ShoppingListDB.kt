package com.example.shoppinglistapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglistapp.model.LibraryItem
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.ShopListItem
import com.example.shoppinglistapp.model.ShopListNameItem

@Database(entities = [LibraryItem::class, NoteItem::class, ShopListItem::class, ShopListNameItem::class], version = 1)
abstract class ShoppingListDB : RoomDatabase() {

    abstract fun getDao(): ShoppingListDao
    companion object{
        @Volatile
        private var INSTANCE: ShoppingListDB? = null
        fun getDataBase(context: Context): ShoppingListDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingListDB::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}