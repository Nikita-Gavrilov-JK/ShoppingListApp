package com.example.shoppinglistapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglistapp.model.LibraryItem
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.ShoppingListItem
import com.example.shoppinglistapp.model.ShoppingListNames

@Database(entities = [LibraryItem::class, NoteItem::class, ShoppingListItem::class, ShoppingListNames::class], version = 1)
abstract class ShoppingListDB : RoomDatabase() {
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