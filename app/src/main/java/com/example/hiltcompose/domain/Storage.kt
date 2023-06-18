package com.example.hiltcompose.domain

import android.content.SharedPreferences

class Storage(prefs: SharedPreferences) {

    private var savedText = "Empty"

    fun getSavedText(): String {
        return "I'm injected!"
    }

    fun setSavedText(text: String) {
        savedText = text
    }
}