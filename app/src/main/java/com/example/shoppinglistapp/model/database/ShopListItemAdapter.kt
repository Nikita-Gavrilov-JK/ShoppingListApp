package com.example.shoppinglistapp.model.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ListNameItemBinding
import com.example.shoppinglistapp.databinding.ShopListItemBinding
import com.example.shoppinglistapp.model.ShopListItem

class ShopListItemAdapter(private val listener: Listener) : ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0 ) {
            ItemHolder.createShopItem(parent)
        }
        else ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else holder.setLibraryData(getItem(position), listener)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
         //поменять binding
        fun setItemData(shopListItem: ShopListItem, listener: Listener) {
             val binding = ShopListItemBinding.bind(view)
             binding.apply {
                 tvName.text = shopListItem.name
                 tvInfo.text = shopListItem.itemInfo
                 tvInfo.visibility = infoVisible(shopListItem)
             }
        }
        fun setLibraryData(shopListItem: ShopListItem, listener: Listener) {

        }

        fun infoVisible(shopListItem: ShopListItem): Int {
            return if (shopListItem.itemInfo.isNullOrEmpty()){
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        companion object {
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_item, parent, false)
                )
            }
            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_library_list_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListItem>() {
        override fun areItemsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(shopListNameItem: ShopListItem)
        fun onClickItem(shopListNameItem: ShopListItem)
    }
}