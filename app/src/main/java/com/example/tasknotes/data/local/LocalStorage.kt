package com.example.tasknotes.data.local

import android.content.Context
import com.example.coursework.data.local.BooleanPreferenceDefTrue

class LocalStorage private constructor(context: Context) {
    companion object {
        lateinit var instance: LocalStorage

        fun init(context: Context) {
            instance = LocalStorage(context)
        }
    }

    private val pref = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)

    var isFirst: Boolean by BooleanPreferenceDefTrue(pref)

    var personProfileName: String?
        get() = pref.getString("PROFILE_NAME", "Unknown")
        set(value) = pref.edit().putString("PROFILE_NAME", value).apply()

    var personProfileEmail: String?
        get() = pref.getString("PROFILE_EMAIL", "example@gmail.com")
        set(value) = pref.edit().putString("PROFILE_EMAIL", value).apply()

    var personProfilePicture: String?
        get() = pref.getString("PROFILE_PICTURE", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fperson%2Bicon&psig=AOvVaw0c9KdgiezGMv0JoXTVT3kH&ust=1593682828556000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIDxk6vhq-oCFQAAAAAdAAAAABAD")
        set(value) = pref.edit().putString("PROFILE_PICTURE", value).apply()

    fun clear() {
        pref.edit().clear().apply()
    }
}