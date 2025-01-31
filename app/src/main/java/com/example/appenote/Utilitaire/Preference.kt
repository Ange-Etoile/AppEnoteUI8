package com.example.appenote.Utilitaire

import android.content.Context
import com.example.appenote.Modele.User
import com.google.gson.Gson

/**
 * @param context
 * @param preferenceName is the name of the shared preference that will be created
 * @property _sharedPreference is the object we will use to manage the preferences
 */
class Preference(context: Context?, preferenceName: String) {
    private val _sharedPreference = context!!.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

    /**
     * This function is used to add a preference
     * @param name is the name of preference
     * @param value is the value of preference
     */
    fun addPreference(name: String, value: Any) {
        val editor = _sharedPreference.edit()
        when (value) {
            is String -> editor.putString(name, value)
            is Int -> editor.putInt(name, value)
            is Boolean -> editor.putBoolean(name, value)
            is Long -> editor.putLong(name, value)
            is Float -> editor.putFloat(name, value)
            is User -> {
                // Convertir l'objet utilisateur en JSON pour le stocker
                val userJson = Gson().toJson(value)
                editor.putString(name, userJson)
                println(userJson)
            }
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
        editor.apply()
    }

    /**
     * This function is used to retrieve a preference
     * @param name is the name of preference
     * @return this function returns the value corresponding to the name of preference passed as a parameter
     */
    fun getStringPreferenceValue(name: String): String? {
        return _sharedPreference.getString(name, "")
    }

    /**
     * This function is used to retrieve a preference
     * @param name is the name of preference
     * @return this function returns the value corresponding to the name of preference passed as a parameter
     */
    fun getIntPreferenceValue(name: String): Int{
        return _sharedPreference.getInt(name, -1)
    }
    /**
     * This function is used to retrieve a preference
     * @param name is the name of preference
     * @return this function returns the value corresponding to the name of preference passed as a parameter
     */
    fun getUserPreferenceValue(name: String): User?{
        val user_json = _sharedPreference.getString(name,null);
        return if (user_json != null){
            Gson().fromJson(user_json, User::class.java)
        }else{
            null
        }

    }

    /**
     * This function is used to retrieve a preference
     * @param name is the name of preference
     * @return this function returns the value corresponding to the name of preference passed as a parameter
     */
    fun getBooleanPreferenceValue(name: String): Boolean{
        return _sharedPreference.getBoolean(name, false)
    }

    /**
     * This function is used to remove a preference
     * @param name is the name of preference
     */
    fun removePreference(name: String) {
        val editor = _sharedPreference.edit()
        editor.remove(name)
        editor.apply()
    }

    /**
     * This function is used to remove all the preference created.
     * It is used when the user logs out of the application
     */
    fun removeAllPreference() {
        _sharedPreference.edit().clear().apply()
    }
}
