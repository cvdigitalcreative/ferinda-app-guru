package com.digitalcreative.appguru.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private var preference: SharedPreferences

    companion object {
        private const val PREFERENCE_FILENAME = "user_preferences"
        const val KEY_NIP = "nip"
        const val KEY_CERTIFICATE = "certificate"
    }

    init {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        preference = EncryptedSharedPreferences.create(
            context,
            PREFERENCE_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setString(key: String, value: String) {
        preference.edit {
            putString(key, value)
        }
    }

    fun getString(key: String): String {
        return preference.getString(key, null) ?: ""
    }

    fun remove(key: String) {
        preference.edit {
            remove(key)
        }
    }
}