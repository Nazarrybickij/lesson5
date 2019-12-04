package com.geekhub.rybickiylesson5

import android.content.SharedPreferences

class Cash(var pref: SharedPreferences) {
    companion object {
        private val APP_PREFERENCES_COUNTER = "counter"
        private val APP_PREFERENCES_TEMPSET = "tempcounter"
        val KELVINCASH = "kelvin"
        val CELSIUSCASH = "celsius"
        val DEFOLT_LOC = "Cherkasy,ua"
        val APP_PREFERENCES = "mysettings"
        var counter = ""
        var tempSet = ""
    }


    fun saveCash(cash: String) {
        val editor = pref.edit()
        editor.putString(APP_PREFERENCES_COUNTER, cash)
        editor.apply()
    }

    fun getCash(): String {
        if (pref.contains(APP_PREFERENCES_COUNTER)) {
            counter = pref.getString(APP_PREFERENCES_COUNTER, DEFOLT_LOC).toString()
            return counter
        }
        return DEFOLT_LOC
    }

    fun saveCashTemp(cash: String) {
        val editor = pref.edit()
        editor.putString(APP_PREFERENCES_TEMPSET, cash)
        editor.apply()
    }

    fun getCashTemp(): String {
        if (pref.contains(APP_PREFERENCES_TEMPSET)) {
            tempSet = pref.getString(APP_PREFERENCES_TEMPSET, DEFOLT_LOC).toString()
            return tempSet
        }
        return CELSIUSCASH

    }
}