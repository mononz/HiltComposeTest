package com.example.hiltcompose.domain

import android.content.SharedPreferences

class Storage(prefs: SharedPreferences) {

    private var savedText = "Empty"

    fun getSavedText(): String {
        return savedText
    }

    fun setSavedText(text: String) {
        savedText = text
    }
}