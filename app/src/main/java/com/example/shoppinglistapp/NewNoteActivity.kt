package com.example.shoppinglistapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglistapp.databinding.ActivityNewNoteBinding
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.utils.HtmlManager
import com.example.shoppinglistapp.utils.MyTouchListener
import com.example.shoppinglistapp.utils.TimeManager
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
        binding.colorPicker.visibility = View.GONE
        actionBarSettings()
        getNote()
        onClickColorPicker()
        init()
    }

    //Метод init служит для перетаскивания палитры цветов
    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }

    private fun onClickColorPicker() = with(binding){
        imRed.setOnClickListener{
            setColorForSelectedText(R.color.pic_red)
        }
        imBlack.setOnClickListener{
            setColorForSelectedText(R.color.pic_black)
        }
        imOrange.setOnClickListener{
            setColorForSelectedText(R.color.pic_orange)
        }
        imBlue.setOnClickListener{
            setColorForSelectedText(R.color.pic_blue)
        }
        imGreen.setOnClickListener{
            setColorForSelectedText(R.color.pic_green)
        }
        imYellow.setOnClickListener{
            setColorForSelectedText(R.color.pic_yellow)
        }
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
        edDescription.setText(HtmlManager.getFromHtml(note?.content!!).trim())
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
        } else if (item.itemId == R.id.id_bold){
            setBoldForSelectedText()
        } else if (item.itemId == R.id.id_color_picker){
            if (binding.colorPicker.isShown){
                closeColorPicker()
            } else {openColorPicker()}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForSelectedText() = with(binding){
        val startPos = edDescription.selectionStart
        val endPos =  edDescription.selectionEnd
        val styles = edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()){
            edDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD_ITALIC)
        }

        edDescription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDescription.text.trim()
        edDescription.setSelection(startPos)
    }

    private fun setColorForSelectedText(colorId: Int) = with(binding){
        val startPos = edDescription.selectionStart
        val endPos =  edDescription.selectionEnd
        val styles = edDescription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)
        if (styles.isNotEmpty()){
            edDescription.text.removeSpan(styles[0])
        }

        edDescription.text.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(this@NewNoteActivity, colorId)),
            startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDescription.text.trim()
        edDescription.setSelection(startPos)
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
            content = HtmlManager.toHtml(edDescription.text)
        )
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.toHtml(binding.edDescription.text),
            TimeManager.getCurrentTime(),
            ""
            )
    }

    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openColorPicker(){
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        binding.colorPicker.startAnimation(openAnim)
    }
}