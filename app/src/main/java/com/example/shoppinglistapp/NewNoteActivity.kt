package com.example.shoppinglistapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglistapp.databinding.ActivityNewNoteBinding
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.view.NoteFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewNoteBinding
    private var note: NoteItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getNote()
    }

    private fun getNote() {
        // Попытка получить заметку из Intent
        val sNote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY, NoteItem::class.java)
        } else {
            intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY) as? NoteItem // Используем безопасное приведение
        }

        // Если заметка не null, заполняем поля. Иначе - это новая заметка.
        if (sNote != null) {
            note = sNote
            fillNote()
        } else {
            // Если это новая заметка, просто оставляем поля пустыми
            note = null
        }
    }



    private fun fillNote() = with(binding) {
        edTitle.setText(note?.title)
        edDescription.setText(note?.content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_save){
            setMainResult()
        } else if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMainResult() {
        var editState = "new" // По умолчанию - новая заметка
        val tempNote: NoteItem? = if (note == null) {
            createNewNote() // Создаем новую заметку
        } else {
            editState = "update" // Если есть существующая заметка, обновляем её
            updateNote()
        }

        // Возвращаем результат
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }


    private fun updateNote(): NoteItem? = with(binding){
        return note?.copy(title = edTitle.text.toString(),
            content = edDescription.text.toString()
        )
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            binding.edDescription.text.toString(),
            getCurrentTime(),
            ""
            )
    }
    private fun getCurrentTime(): String {
        val format = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return format.format(Calendar.getInstance().time)
    }

    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}