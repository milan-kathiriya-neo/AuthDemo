package com.example.authdemo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile

fun createDataStoreAndroid(context: Context): DataStore<Preferences> = createDataStore {
    context.preferencesDataStoreFile(DATA_STORE_FILE_NAME).absolutePath
}