package com.summerland.android.twozerofoureightapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast


    class MainActivity : AppCompatActivity() {

    private var x1 = 0f
    private var y1 = 0f
    private lateinit var game: TwoZeroFourEight
    private var cells = IntArray(16)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cells = IntArray(16)
        cells[0] = R.id.index0
        cells[1] = R.id.index1
        cells[2] = R.id.index2
        cells[3] = R.id.index3
        cells[4] = R.id.index4
        cells[5] = R.id.index5
        cells[6] = R.id.index6
        cells[7] = R.id.index7
        cells[8] = R.id.index8
        cells[9] = R.id.index9
        cells[10] = R.id.index10
        cells[11] = R.id.index11
        cells[12] = R.id.index12
        cells[13] = R.id.index13
        cells[14] = R.id.index14
        cells[15] = R.id.index15

        Log.i("Logm", "Inside on create main activity method")

        if (savedInstanceState != null) {
            val tmpGame :TwoZeroFourEight? = savedInstanceState.getSerializable(GAME_KEY) as TwoZeroFourEight
            if (tmpGame != null) {
                game = tmpGame
                game.rePlot()
            }
        } else {
            game = TwoZeroFourEight()
        }
        paintTiles()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("Logm", "Inside onSaveInstanceState method")
        outState.putSerializable(GAME_KEY, game)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("Logm", "Inside onRestoreInstanceState method")
        if (savedInstanceState != null) {
            val tmpGame :TwoZeroFourEight? = savedInstanceState.getSerializable(GAME_KEY) as TwoZeroFourEight
            if (tmpGame != null) {
                game = tmpGame
                game.rePlot()
            }
        }
        paintTiles()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClick(view: View) {
        Log.i("Logm", "Inside on click main activity action method")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    // onTouchEvent () method gets called when User performs any touch event on screen
    // Method to handle touch event like left to right swap and right to left swap
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
        // when user first touches the screen we get x and y coordinate
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                y1 = event.y
            }
            MotionEvent.ACTION_UP -> {
                val x2 = event.x
                val y2 = event.y

                val minDistance = 200
                var moved = false

                //if left to right sweep event on screen
                if (x1 < x2 && x2 - x1 > minDistance) {
                    //Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_SHORT).show();
                    moved = game.actionMove(Moves.Right)
                }

                // if right to left sweep event on screen
                //Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_SHORT).show();
                if (x1 > x2 && x1 - x2 > minDistance) {
                    moved = game.actionMove(Moves.Left)
                }

                // if UP to Down sweep event on screen
                if (y1 < y2 && y2 - y1 > minDistance) {
                    //Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_SHORT).show();
                    moved = game.actionMove(Moves.Down)
                }

                //if Down to UP sweep event on screen
                if (y1 > y2 && y1 - y2 > minDistance) {
                    //Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_SHORT).show();
                    moved = game.actionMove(Moves.Up)
                }

                if (moved) paintTiles() // repaint if something moved.

            }
        }
        return super.onTouchEvent(event)
    }

    private fun paintTiles() {

        if (game.maxTile >= TwoZeroFourEight.TARGET) {
            Toast.makeText(this, resources.getString(R.string.winner_toast_message) +
                    game.maxTile, Toast.LENGTH_LONG).show()

        } else if (!game.hasMovesRemaining()) {
            Toast.makeText(this, resources.getString(R.string.lose_toast_message),
                    Toast.LENGTH_LONG).show()
        }

        //Log.i("Logm", game.toString())
        val tv: TextView = findViewById(R.id.score) as TextView
        tv.text = StringBuffer(resources.getString(R.string.score)).append(game.score)

        val transitions = game.transitions
        if (transitions.size == 0) return

        for (trans in transitions) {
            paintTransition(trans)
        }
    }

    private fun paintTransition(trans: TwoZeroFourEight.Transition) {

        val tv = findViewById(cells[trans.newLocation]) as TextView

        if (trans.type == Actions.Compact) {
            //Log.i("Logm", "About to do paint compact")
            //Log.i("Logm", "About to do paint compact w=$w h=$h t=$t")
            try {
                paintCell(tv, trans.value)
                //Thread.sleep(100)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                paintCell(tv, trans.value)
            }
        } else {
            paintCell(tv, trans.value)
        }
    }

    private fun paintCell(obj: Any, value: Int) {

        val tv = obj as TextView

        if (value == 0) {
            tv.text = ""
        } else {
            tv.text = StringBuffer("").append(value)
        }

        var txCol = resources.getColor(R.color.t_dark_text, null)
        var bgCol = resources.getColor(R.color.t0_bg, null)

        when (value) {
            2 -> bgCol = resources.getColor(R.color.t2_bg, null)
            4 -> bgCol = resources.getColor(R.color.t4_bg, null)
            8 -> {
                txCol = resources.getColor(R.color.t_light_text, null)
                bgCol = resources.getColor(R.color.t8_bg, null)
            }
            16 -> {
                txCol = resources.getColor(R.color.t_light_text, null)
                bgCol = resources.getColor(R.color.t16_bg, null)
            }
            32 -> {
                txCol = resources.getColor(R.color.t_light_text, null)
                bgCol = resources.getColor(R.color.t32_bg, null)
            }
            64 -> {
                txCol = resources.getColor(R.color.t_light_text, null)
                bgCol = resources.getColor(R.color.t64_bg, null)
            }
            128 -> {
                txCol = resources.getColor(R.color.t_light_text, null)
                bgCol = resources.getColor(R.color.t128_bg, null)
            }
            256 -> bgCol = resources.getColor(R.color.t256_bg, null)
            512 -> bgCol = resources.getColor(R.color.t512_bg, null)
            1024 -> bgCol = resources.getColor(R.color.t1024_bg, null)
            2048 -> bgCol = resources.getColor(R.color.t2048_bg, null)
            else -> {
            }
        }
        tv.setBackgroundColor(bgCol)
        tv.setTextColor(txCol)
    }

    companion object {
        private const val GAME_KEY = "2048_GAME_KEY"
    }
}
