package com.summerland.android.twozerofoureightapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.summerland.android.twozerofoureightapp.databinding.ActivityMainBinding
import com.summerland.android.tzfe.game.engine.*

class MainActivity : AppCompatActivity(), GameEngineProtocol {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var x1 = 0f
    private var y1 = 0f

    private lateinit var game: TwoZeroFourEight
    private lateinit var dataStore : StoredDataUtils
    private var highScore : Int = 0
    private val cells = IntArray(16)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setContentView(R.layout.activity_main)
        dataStore = StoredDataUtils(this.baseContext)

        Log.i("Logm", "Inside on create main activity method")

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

        if (savedInstanceState != null) {
            val tmpGame : TwoZeroFourEight? = savedInstanceState.getSerializable(GAME_KEY) as TwoZeroFourEight?
            if (tmpGame != null) {
                game = tmpGame
                game.replotBoard()
            }
        } else {
            game = TwoZeroFourEight(this)
            this.setupNewGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("Logm", "Inside onSaveInstanceState method")
        outState.putSerializable(GAME_KEY, game)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("Logm", "Inside onRestoreInstanceState method")
        val tmpGame :TwoZeroFourEight? = savedInstanceState.getSerializable(GAME_KEY) as TwoZeroFourEight?
        if (tmpGame != null) {
            game = tmpGame
            game.replotBoard()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
//            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

    @Suppress("UNUSED_PARAMETER")
    fun onClick(view: View) {
        Log.i("Logm", "Inside on click main activity action method")
        this.setupNewGame()
    }

    // Start / Reset game here ->
    private fun setupNewGame() {
        this.highScore = dataStore.getHighscore()
        //this.gamePanel.resetBoard()
        game.newGame(this.highScore)
        this.userScoreChanged(0)
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

                if (x1 < x2 && x2 - x1 > minDistance) { game.actionMove(GameMoves.Right) }
                else if (x1 > x2 && x1 - x2 > minDistance) { game.actionMove(GameMoves.Left) }
                else if (y1 < y2 && y2 - y1 > minDistance) { game.actionMove(GameMoves.Down) }
                else if (y1 > y2 && y1 - y2 > minDistance) { game.actionMove(GameMoves.Up) }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun paintTransition(move: GameEngine.Transition) {

        val tv = findViewById<TextView>(cells[move.location])

        if (move.action == TileMoveType.Slide || move.action == TileMoveType.Merge) {
            //Log.i("Logm", "About to do paint compact")
            //Log.i("Logm", "About to do paint compact w=$w h=$h t=$t")
            paintCell(tv, move.value)
            this.paintTransition(GameEngine.Transition(TileMoveType.Clear, 0, move.oldLocation))
        } else {
            paintCell(tv, move.value)
        }
    }

    private fun paintCell(obj: Any, value: Int) {

        val tv = obj as TextView
        tv.text = if (value <= 0) "" else "$value"

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
            else -> { } // won't happen
        }
        tv.setBackgroundColor(bgCol)
        tv.setTextColor(txCol)
    }

    override fun userWin() {
        Toast.makeText(this, resources.getString(R.string.winner_toast_message) +
                game.maxTile, Toast.LENGTH_LONG).show()
    }

    override fun userFail() {
        Toast.makeText(this, resources.getString(R.string.lose_toast_message),
            Toast.LENGTH_LONG).show()
    }

    override fun userPB(score: Int) {
        Toast.makeText(this, resources.getString(R.string.personalbest_toast_message),
            Toast.LENGTH_SHORT).show()
        this.dataStore.putHighscore(score)
        this.highScore = score
    }

    override fun userScoreChanged(score: Int) {
        val tv: TextView = findViewById(R.id.score)
        tv.text = StringBuffer(resources.getString(R.string.score)).append(score)
    }

    override fun updateTileValue(move: GameEngine.Transition) {
        paintTransition(move)
    }

    companion object {
        private const val GAME_KEY = "2048_GAME_KEY"
    }

}