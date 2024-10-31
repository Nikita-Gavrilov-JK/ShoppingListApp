package com.example.shoppinglistapp.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.ShoppingListName
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    // Считываем все данные из таблицы note_list используя Flow(класс из корутинов) это специальный класс будет подключать БД к нашему списку
    // Flow это как бы поток
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShoppingListName>>

    // Добавление обновление и удаление заметок
    @Insert
    suspend fun insertNote(note: NoteItem)

    @Query("DELETE FROM note_list WHERE id = :id")
    suspend fun deleteNote(id: Int)
    @Update
    suspend fun updateNote(note: NoteItem)

    // Добавление обновление и удаление списков покупок
    @Insert
    suspend fun insertShopListName(listName: ShoppingListName)
    @Query("DELETE FROM shopping_list_names WHERE id = :id")
    suspend fun deleteShopListName(id: Int)
}