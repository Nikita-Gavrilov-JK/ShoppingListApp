package com.example.shoppinglistapp.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.NewListDialogBinding

//Класс чтобы не нужно было инициализировать чтобы сразу запускать функцию которая будет запускать наш диалог
object NewListDialog {
    fun showDialog(context: Context, listener: Listener, name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            edNewListName.setText(name)
            if (name.isNotEmpty()) {
                tvNewDialogTitle.text = context.getString(R.string.update_new_dialog_title)
                bCreate.text = context.getString(R.string.update_button_shop_list_name)
            }
            bCreate.setOnClickListener{
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()){
                    listener.onClick(listName)
                }

                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String)
    }
}