package com.example.shoppinglistapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding
    override fun onClickNew() {
        TODO("Not yet implemented")
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