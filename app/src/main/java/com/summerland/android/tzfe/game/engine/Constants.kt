package com.summerland.android.tzfe.game.engine

import android.graphics.Color
import android.provider.FontRequest
import android.support.annotation.FontRes

class Constants {
    companion object {
        // STD message strings EN
        const val NO_MORE_MOVES = "Sorry. No more moves possible. Try Undo?"
        const val NO_MORE_UNDO = "Sorry. No undo's available. Undo's are limited to 5!"
        const val NEW_HIGH_SCORE = "Oh YES. New PB reached."
        const val WINNER = "AWESOME!, you have won the game. Keep playing!!!"

        // Game specific values
        const val WIN_TARGET = 2048
        const val EMPTY_TILE_VAL = 0
        const val MAX_PREVIOUS_MOVES = 5
        const val PB_MESG_THRESHOLD = 100

        // Board specifics
        const val DIMENSION = 4
        const val TILE_CNT = 16
        const val TILE_WIDTH = 65.5
        const val TILE_PADDING = 8.0
        const val BOARD_CORNER_RADIUS = 2.0
        const val TILE_CORNER_RADIUS = 5

        // Animation duration in seconds
        const val ZERO_DURATION = 0.00
        const val QUICK_DURATION = 0.09
        const val NORMAL_DURATION = 0.25
        const val SLOW_DURATION = 0.45
        const val LONG_DURATION = 0.60
        const val PAUSE_DURATION = 1.00

        // Fonts for various occasions
        ///val SMALL_NUMBER_FONTS : ()->(name:String, size:Int) : ()-> { return (name:"HelveticaNeue-Bold", size:38) }
        //val MEDIUM_NUMBER_FONTS = (name: "HelveticaNeue-Bold", size: 36)
        //val LARGE_NUMBER_FONTS = (name: "HelveticaNeue-Bold", size: 28)

        // Random tile selection ratio
        const val RANDOM_RATIO = 70  // favours '2's over '4's by ~3.5:1 ~70%

        // Audio files
        const val HORRAY_AUDIO_FN = "cheer.mp3"        // A MP# FILE
        const val SAD_AUDIO_FN = "negative-beeps.wav"  // A WAV FILE
        const val MP3_AUDIO = "mp3"                    // A WAV FILE
        const val WAV_AUDIO = "wav"                    // A WAV FILE
        const val AUDIO_SUBDIRECTORY = "sound.assets/"

        // Images
        //val SOUND_ON_IMG = #imageLiteral(resourceName: "sound_on")
        //val SOUND_OFF_IMG = #imageLiteral(resourceName: "sound_off")

        // All the tile colours
        val GAME_BOARD_COLOUR = Color.valueOf(187.0f / 255, 173.0f / 255, 160.0f / 255, 1.0f)
        val TILE_BG_COLOUR = Color.valueOf(204.0f / 255, 192.0f / 255, 179.0f / 255f, 1.0f)
        val TILE2_COLOUR = Color.valueOf(238.0f / 255, 228.0f / 255, 244.0f / 255, 1.0f)
        val TILE4_COLOUR = Color.valueOf(237.0f / 255, 224.0f / 255, 200.0f / 255, 1.0f)
        val TILE8_COLOUR = Color.valueOf(245.0f / 255, 149.0f / 255, 99.0f / 255, 1.0f)
        val TILE16_COLOUR = Color.valueOf(245.0f / 255, 130.0f / 255, 97.0f / 255, 1.0f)
        val TILE32_COLOUR = Color.valueOf(246.0f / 255, 124.0f / 255, 95.0f / 255, 1.0f)
        val TILE64_COLOUR = Color.valueOf(246.0f / 255, 94.0f / 255, 59.0f / 255, 1.0f)
        val TILE128_COLOUR = Color.valueOf(237.0f / 255, 207.0f / 255, 114.0f / 255, 1.0f)
        val TILE256_COLOUR = Color.valueOf(237.0f / 255, 207.0f / 255, 114.0f / 255, 1.0f)
        val TILE512_COLOUR = Color.valueOf(237.0f / 255, 200.0f / 255, 80.0f / 255, 1.0f)
        val TILE1024_COLOUR = Color.valueOf(237.0f / 255, 197.0f / 255, 63.0f / 255, 1.0f)
        val TILE2048_COLOUR = Color.valueOf(237.0f / 255, 194.0f / 255, 46.0f / 255, 1.0f)
        val DEFAULT_COLOUR = Color.DKGRAY
    }
}