package com.summerland.android.tzfe.game.engine

import android.content.Context
import com.summerland.android.twozerofoureightapp.R

open class StoredDataUtils(context: Context) {

    private val preferenceKey = context.getString(R.string.game_data_preference_key)
    private val highscoreKey = context.getString(R.string.game_data_highest_score_int_key)
    private val sPref = context.getSharedPreferences(this.preferenceKey, Context.MODE_PRIVATE)

    // Push highscore data to user defaults
    fun putHighscore(newHS : Int) {
        val editor = sPref.edit()
        editor.putInt(this.highscoreKey, newHS)
        editor.apply()
    }

    // Get highscore data from user defaults
    fun getHighscore() : Int {
        return this.sPref.getInt(this.highscoreKey, -1)
    }
}