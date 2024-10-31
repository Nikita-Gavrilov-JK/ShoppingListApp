package com.example.shoppinglistapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.MainApp
import com.example.shoppinglistapp.databinding.FragmentShopListNamesBinding
import com.example.shoppinglistapp.model.NoteItem
import com.example.shoppinglistapp.model.ShoppingListName
import com.example.shoppinglistapp.model.database.ShopListNameAdapter
import com.example.shoppinglistapp.utils.TimeManager
import com.example.shoppinglistapp.view.dialog.DeleteDialog
import com.example.shoppinglistapp.view.dialog.NewListDialog
import com.example.shoppinglistapp.viewmodel.ShoppingListViewModel

class ShopListNamesFragment : BaseFragment(), ShopListNameAdapter.Listener {
    private lateinit var binding: FragmentShopListNamesBinding
    private lateinit var adapter: ShopListNameAdapter


    private val shoppingListViewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory((context?.applicationContext as MainApp).database)
    }
    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener{
            override fun onClick(name: String) {
                val shopListName = ShoppingListName(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                shoppingListViewModel.insertShopListName(shopListName)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }
    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ShopListNameAdapter(this@ShopListNamesFragment)
        rcView.adapter = adapter
    }

    private fun observer() {
        shoppingListViewModel.allShopListNames.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }



    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }

    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.Listener{
            override fun onClick() {
                shoppingListViewModel.deleteShopListName(id)
            }
        })
    }

    override fun onClickItem(note: NoteItem) {

    }

}