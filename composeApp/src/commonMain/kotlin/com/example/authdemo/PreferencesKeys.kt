package com.example.authdemo

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    const val USER_DATA_KEY = "userDataKey"

    val USER_DATA_PREF_KEY = stringPreferencesKey(USER_DATA_KEY)
}