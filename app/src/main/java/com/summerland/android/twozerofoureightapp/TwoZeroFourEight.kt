/*
 * Created by steve on 17/10/16.
 * Grid is from bottom left to right, bottom to top.
 */

package com.summerland.android.twozerofoureightapp

import java.io.Serializable
import java.util.ArrayList
import java.util.Random


enum class Moves { Left, Right, Up, Down }
enum class Actions { Add, Blank, Slide, Compact, Refresh }

class TwoZeroFourEight internal constructor() : Serializable {
    var score = 0
        private set
    private var numEmpty = 16
    var maxTile = 0
        private set

    lateinit var transitions: ArrayList<Transition>
        private set
    private val tiles = IntArray(16)
    private val rand = Random()

    init {
        this.createNewTransitions()
        this.addNewTile()
        this.addNewTile()
    }

    private fun createNewTransitions() {
        transitions = ArrayList()
    }

    fun rePlot() {
        this.createNewTransitions()
        for (i in 0..(GRID_CNT-1)) {
            this.transitions.add(Transition(Actions.Refresh, tiles[i], i))
        }
    }

    private fun addNewTile() {
        if (numEmpty == 0) return

        val value = (rand.nextInt(2) + 1) * 2
        val pos = rand.nextInt(numEmpty)
        var blanks = 0

        for (i in 0..(GRID_CNT-1)) {
            if (tiles[i] == BLANK) {
                if (blanks == pos) {
                    tiles[i] = value
                    if (value > maxTile) maxTile = value
                    numEmpty--
                    transitions.add(Transition(Actions.Add, value, i))
                    return
                }
                blanks++
            }
        }
    }

    fun getValue(i: Int): Int {
        return tiles[i]
    }

    fun hasMovesRemaining(): Boolean {

        if (numEmpty > 0) return true

        // check left-right for compact moves remaining.
        var arrLimit = (GRID_CNT - COL_CNT) - 1
        for (i in 0..arrLimit) {
            if (tiles[i] == tiles[i + COL_CNT]) return true
        }

        // check up-down for compact moves remaining.
        arrLimit = GRID_CNT - 2
        for (i in 0..arrLimit) {
            if ((i + 1) % ROW_CNT > 0) {
                if (tiles[i] == tiles[i + 1]) return true
            }
        }
        return false
    }

    private fun slideTileRowOrColumn(index1: Int, index2: Int, index3: Int, index4: Int): Boolean {

        var moved = false
        val tmpArr = intArrayOf(index1,index2,index3,index4)

        // Do we have some sliding to do, or not?
        var es = 0  // empty spot index
        for (j in 0..(tmpArr.size-1)) {
            if (tiles[tmpArr[es]] != BLANK) {
                es++
                continue
            } else if (tiles[tmpArr[j]] == BLANK) {
                continue
            } else {
                // Otherwise we have a slide condition
                tiles[tmpArr[es]] = tiles[tmpArr[j]]
                tiles[tmpArr[j]] = BLANK
                transitions.add(Transition(Actions.Slide, tiles[tmpArr[es]], tmpArr[es], tmpArr[j]))
                transitions.add(Transition(Actions.Blank, BLANK, tmpArr[j]))
                moved = true
                es++
            }
        }
        return moved
    }

    private fun compactTileRowOrColumn(index1: Int, index2: Int, index3: Int, index4: Int): Boolean {

        var compacted = false
        val tmpArr = intArrayOf(index1, index2, index3, index4)

        for (j in 0..(tmpArr.size-2)) {

            if (tiles[tmpArr[j]] != BLANK && tiles[tmpArr[j]] == tiles[tmpArr[j+1]]) { // we found a matching pair
                val ctv = tiles[tmpArr[j]] * 2   // = compacted tile value
                tiles[tmpArr[j]] = ctv
                tiles[tmpArr[j+1]] = BLANK
                score += ctv
                if (ctv > maxTile) {
                    maxTile = ctv
                }  // is this the biggest tile # so far
                transitions.add(Transition(Actions.Compact, ctv, tmpArr[j], tmpArr[j+1]))
                transitions.add(Transition(Actions.Blank, BLANK, tmpArr[j+1]))
                compacted = true
                numEmpty++
            }
        }
        return compacted
    }

    private fun slideLeft(): Boolean {
        val a = slideTileRowOrColumn(0, 4, 8, 12)
        val b = slideTileRowOrColumn(1, 5, 9, 13)
        val c = slideTileRowOrColumn(2, 6, 10, 14)
        val d = slideTileRowOrColumn(3, 7, 11, 15)
        return a || b || c || d
    }

    private fun slideRight(): Boolean {
        val a = slideTileRowOrColumn(12, 8, 4, 0)
        val b = slideTileRowOrColumn(13, 9, 5, 1)
        val c = slideTileRowOrColumn(14, 10, 6, 2)
        val d = slideTileRowOrColumn(15, 11, 7, 3)
        return a || b || c || d
    }

    private fun slideUp(): Boolean {
        val a = slideTileRowOrColumn(0, 1, 2, 3)
        val b = slideTileRowOrColumn(4, 5, 6, 7)
        val c = slideTileRowOrColumn(8, 9, 10, 11)
        val d = slideTileRowOrColumn(12, 13, 14, 15)
        return a || b || c || d
    }

    private fun slideDown(): Boolean {
        val a = slideTileRowOrColumn(3, 2, 1, 0)
        val b = slideTileRowOrColumn(7, 6, 5, 4)
        val c = slideTileRowOrColumn(11, 10, 9, 8)
        val d = slideTileRowOrColumn(15, 14, 13, 12)
        return a || b || c || d
    }

    private fun compactLeft(): Boolean {
        val a = compactTileRowOrColumn(0, 4, 8, 12)
        val b = compactTileRowOrColumn(1, 5, 9, 13)
        val c = compactTileRowOrColumn(2, 6, 10, 14)
        val d = compactTileRowOrColumn(3, 7, 11, 15)
        return a || b || c || d
    }

    private fun compactRight(): Boolean {
        val a = compactTileRowOrColumn(12, 8, 4, 0)
        val b = compactTileRowOrColumn(13, 9, 5, 1)
        val c = compactTileRowOrColumn(14, 10, 6, 2)
        val d = compactTileRowOrColumn(15, 11, 7, 3)
        return a || b || c || d
    }

    private fun compactUp(): Boolean {
        val a = compactTileRowOrColumn(0, 1, 2, 3)
        val b = compactTileRowOrColumn(4, 5, 6, 7)
        val c = compactTileRowOrColumn(8, 9, 10, 11)
        val d = compactTileRowOrColumn(12, 13, 14, 15)
        return a || b || c || d
    }

    private fun compactDown(): Boolean {
        val a = compactTileRowOrColumn(3, 2, 1, 0)
        val b = compactTileRowOrColumn(7, 6, 5, 4)
        val c = compactTileRowOrColumn(11, 10, 9, 8)
        val d = compactTileRowOrColumn(15, 14, 13, 12)
        return a || b || c || d
    }

    private fun actionMoveLeft(): Boolean {
        val a = slideLeft()
        val b = compactLeft()
        val c = slideLeft()
        return a || b || c
    }

    private fun actionMoveRight(): Boolean {
        val a = slideRight()
        val b = compactRight()
        val c = slideRight()
        return a || b || c
    }

    private fun actionMoveUp(): Boolean {
        val a = slideUp()
        val b = compactUp()
        val c = slideUp()
        return a || b || c
    }

    private fun actionMoveDown(): Boolean {
        val a = slideDown()
        val b = compactDown()
        val c = slideDown()
        return a || b || c
    }


    // Central game move trigger
    // Central game move trigger
    fun actionMove(move: Moves): Boolean {

        this.createNewTransitions()
        if (!hasMovesRemaining()) return false

        val result = when (move) {
            Moves.Left -> actionMoveLeft()
            Moves.Right -> actionMoveRight()
            Moves.Up -> actionMoveUp()
            Moves.Down -> actionMoveDown()
        }

        if (result) {
            addNewTile()
        }
        return result
    }

    override fun toString(): String {
        return "TwoZeroFourEight:class\n" +
                "--------------------\n" +
                "|" + tiles[0] + "|" + tiles[4] + "|" + tiles[8] + "|" + tiles[12] + "|\n" +
                "|" + tiles[1] + "|" + tiles[5] + "|" + tiles[9] + "|" + tiles[13] + "|\n" +
                "|" + tiles[2] + "|" + tiles[6] + "|" + tiles[10] + "|" + tiles[14] + "|\n" +
                "|" + tiles[3] + "|" + tiles[7] + "|" + tiles[11] + "|" + tiles[15] + "|\n" +
                "--------------------\n"
    }


    inner class Transition : Serializable {

        var type: Actions
        var value = 0
        var newLocation = -1
        var oldLocation = -1

        constructor(action: Actions, value: Int, newLocation: Int, oldLocation: Int) {
            type = action
            this.value = value
            this.newLocation = newLocation
            this.oldLocation = oldLocation
        }

        constructor(action: Actions, value: Int, newLocation: Int) {
            type = action
            this.value = value
            this.newLocation = newLocation
        }
    }

    companion object {
        internal const val TARGET = 2048
        private const val GRID_CNT = 16
        private const val ROW_CNT = 4
        private const val COL_CNT = 4
        private const val BLANK = 0
    }
}
