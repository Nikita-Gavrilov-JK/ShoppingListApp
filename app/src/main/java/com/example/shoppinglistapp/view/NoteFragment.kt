package com.example.shoppinglistapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.shoppinglistapp.MainApp
import com.example.shoppinglistapp.NewNoteActivity
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.FragmentNoteBinding
import com.example.shoppinglistapp.viewmodel.ShoppingListViewModel

class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding
    private val shoppingListViewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory((context?.applicationContext as MainApp).database)
    }
    override fun onClickNew() {
        startActivity(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingListViewModel.allNotes.observe(this, {it})//Excample
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    // companion object нужен нам будет для создания  Singleton
    // Для того чтобы у нас была одна инстанция фрагмента если мы пыттаемся её несколько раз запустить
    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}