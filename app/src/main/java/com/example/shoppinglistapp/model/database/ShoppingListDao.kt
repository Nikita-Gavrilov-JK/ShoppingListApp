package com.example.shoppinglistapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.ShopListItem
import com.example.shoppinglistapp.model.ShopListNameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    // Считываем все данные из таблицы note_list используя Flow(класс из корутинов) это специальный класс будет подключать БД к нашему списку
    // Flow это как бы поток
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShopListNameItem>>

    // Добавление обновление и удаление заметок
    @Insert
    suspend fun insertNote(note: NoteItem)

    @Query("DELETE FROM note_list WHERE id = :id")
    suspend fun deleteNote(id: Int)
    @Update
    suspend fun updateNote(note: NoteItem)

    // Добавление обновление и удаление списков покупок
    @Insert
    suspend fun insertShopListName(listName: ShopListNameItem)
    @Query("DELETE FROM shopping_list_names WHERE id = :id")
    suspend fun deleteShopListName(id: Int)
    @Update
    suspend fun updateShopListName(note: ShopListNameItem)

    // Добавление обновление и удаление продуктов (item)
    @Insert
    suspend fun insertItem(shopListItem: ShopListItem)
    @Update
    suspend fun updateItem(item: ShopListItem)
    @Query("DELETE FROM shop_list_item WHERE listId LIKE :listId")
    suspend fun deleteItem(listId: Int)
    @Query("SELECT * FROM shop_list_item WHERE listId LIKE :listId")
    fun getAllShopListItems(listId: Int): Flow<List<ShopListItem>>
}