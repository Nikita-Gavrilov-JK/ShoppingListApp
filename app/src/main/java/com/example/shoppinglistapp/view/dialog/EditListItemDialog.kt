package com.example.shoppinglistapp.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.EditListItemDialogBinding
import com.example.shoppinglistapp.databinding.NewListDialogBinding
import com.example.shoppinglistapp.model.ShopListItem

//Класс чтобы не нужно было инициализировать чтобы сразу запускать функцию которая будет запускать наш диалог
object EditListItemDialog {
    fun showDialog(context: Context, item: ShopListItem, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            editTextEditDialog.setText(item.name)
            editInfoEditDialog.setText(item.itemInfo)
            if (item.itemType == 1) editInfoEditDialog.visibility = View.GONE
            bUpdate.setOnClickListener{
                if (editTextEditDialog.text.toString().isNotEmpty()){
                    val itemInfo = if(editInfoEditDialog.text.toString().isEmpty()) null else editInfoEditDialog.text.toString()
                    listener.onClick(item.copy(name = editTextEditDialog.text.toString(), itemInfo = itemInfo))
                }
                dialog?.dismiss()
            }

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(item: ShopListItem)
    }
}