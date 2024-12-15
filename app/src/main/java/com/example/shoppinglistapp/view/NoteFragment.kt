package com.example.shoppinglistapp.view

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shoppinglistapp.MainApp
import com.example.shoppinglistapp.NewNoteActivity
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.FragmentNoteBinding
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.database.NoteAdpter
import com.example.shoppinglistapp.viewmodel.ShoppingListViewModel

class NoteFragment : BaseFragment(), NoteAdpter.Listener {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdpter
    private lateinit var defPref: SharedPreferences
    private val shoppingListViewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory((context?.applicationContext as MainApp).database)
    }
    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }
    private fun initRcView() = with(binding){
        defPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        rcViewNote.layoutManager = getLayoutManager()
        adapter = NoteAdpter(this@NoteFragment, defPref)
        rcViewNote.adapter = adapter
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return if (defPref.getString("note_style_key", "Список") == "Список"){
            LinearLayoutManager(activity)
        } else {
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun observer() {
        shoppingListViewModel.allNotes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onEditResult(){
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data ?: return@registerForActivityResult
                val editState = result.data?.getStringExtra(EDIT_STATE_KEY)

                // так как метод getSerializableExtra устарел. Мы прописываем условие,
                // чтобы работало отображение фрагменнта как на старых версиях sdk так и на новых
                val noteItem: NoteItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data.getSerializableExtra(NEW_NOTE_KEY, NoteItem::class.java)
                } else {
                    data.getSerializableExtra(NEW_NOTE_KEY) as? NoteItem
                }

                noteItem?.let { note ->
                    if (editState == "update"){
                        shoppingListViewModel.updateNote(note)
                    }else {
                        shoppingListViewModel.insertNote(note)
                    }
                }
            }
        }
    }

    // companion object нужен нам будет для создания  Singleton
    // Для того чтобы у нас была одна инстанция фрагмента если мы пыттаемся её несколько раз запустить
    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_STATE_KEY = "edit_state_key"
        @JvmStatic
        fun newInstance() = NoteFragment()
    }

    override fun deleteItem(id: Int) {
        shoppingListViewModel.deleteNote(id)
    }

    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity, NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, note)
        }
        editLauncher.launch(intent)
    }
}