package com.example.shoppinglistapp.view

import com.example.shoppinglistapp.R
import androidx.appcompat.app.AppCompatActivity

object FragmentManager {
    var currentFragment: BaseFragment? = null
    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFragment = newFrag
    }
}