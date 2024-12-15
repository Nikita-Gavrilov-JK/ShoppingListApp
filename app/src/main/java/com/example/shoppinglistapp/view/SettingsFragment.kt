package com.example.shoppinglistapp.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.shoppinglistapp.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

    }
}