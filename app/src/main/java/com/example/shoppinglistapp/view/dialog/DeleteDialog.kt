package com.example.shoppinglistapp.view.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.shoppinglistapp.databinding.DeleteDialogBinding
import com.example.shoppinglistapp.databinding.NewListDialogBinding

//Класс чтобы не нужно было инициализировать чтобы сразу запускать функцию которая будет запускать наш диалог
object DeleteDialog {
    fun showDialog(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            buttonDeleteYes.setOnClickListener{
                listener.onClick()
                dialog?.dismiss()
            }
            buttonDeleteNo.setOnClickListener{
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick()
    }
}