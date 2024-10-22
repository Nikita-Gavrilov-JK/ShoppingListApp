package com.example.shoppinglistapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.shoppinglistapp.MainApp
import com.example.shoppinglistapp.databinding.FragmentShopListNamesBinding
import com.example.shoppinglistapp.model.ShoppingListName
import com.example.shoppinglistapp.utils.TimeManager
import com.example.shoppinglistapp.view.dialog.NewListDialog
import com.example.shoppinglistapp.viewmodel.ShoppingListViewModel

class ShopListNamesFragment : BaseFragment() {
    private lateinit var binding: FragmentShopListNamesBinding


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

    }

    private fun observer() {
        shoppingListViewModel.allShopListNames.observe(viewLifecycleOwner, {

        })
    }



    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }

}