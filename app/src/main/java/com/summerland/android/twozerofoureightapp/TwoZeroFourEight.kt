package com.summerland.android.twozerofoureightapp

import java.io.Serializable
import java.util.ArrayList
import java.util.Random

/**
 * Created by steve on 17/10/16.
 * Grid is from bottom left to right, bottom to top.
 */
class TwoZeroFourEight internal constructor() : Serializable {
    var score = 0
        private set
    private var numEmpty = 16
    var maxTile = 0
        private set

    var transitions: ArrayList<Transition>? = null
        private set
    private val tiles = IntArray(16)
    private val rand = Random()

    enum class Actions {
        ADD_NEW, BLANK, SLIDE, COMPACT, REFRESH
    }

    init {
        this.createNewTransitions()
        this.addNewTile()
        this.addNewTile()
    }

    internal fun createNewTransitions() {
        transitions = ArrayList()
    }

    fun rePlot() {
        this.createNewTransitions()
        for (i in 0..GRID_CNT) {
            this.transitions!!.add(Transition(Actions.REFRESH, tiles[i], i))
        }
    }

    internal fun addNewTile() {
        if (numEmpty == 0) return

        val `val` = (rand.nextInt(2) + 1) * 2
        val pos = rand.nextInt(numEmpty)
        var blanks = 0

        for (i in 0..GRID_CNT) {
            if (tiles[i] == BLANK) {
                if (blanks == pos) {
                    tiles[i] = `val`
                    if (`val` > maxTile) maxTile = `val`
                    numEmpty--
                    if (transitions != null)
                        transitions!!.add(Transition(Actions.ADD_NEW, `val`, i))
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
        var arrLimit = GRID_CNT - COL_CNT
        for (i in 0..arrLimit) {
            if (tiles[i] == tiles[i + COL_CNT]) return true
        }

        // check up-down for compact moves remaining.
        arrLimit = GRID_CNT - 1
        for (i in 0..arrLimit) {
            if ((i + 1) % ROW_CNT > 0) {
                if (tiles[i] == tiles[i + 1]) return true
            }
        }
        return false
    }

    private fun slideTileRowOrColumn(index1: Int, index2: Int, index3: Int, index4: Int): Boolean {

        var moved = false
        var val1 = tiles[index1]
        var tmpI = index1
        for (j in 1..COL_CNT) {
            var val2 = 0
            var tmpJ = 0
            when (j) {
                1 -> {
                    val2 = tiles[index2]
                    tmpJ = index2
                }
                2 -> {
                    val2 = tiles[index3]
                    tmpJ = index3
                }
                3 -> {
                    val2 = tiles[index4]
                    tmpJ = index4
                }
                else -> {
                }
            }

            if (val1 == 0 && val2 != 0) {
                tiles[tmpI] = val2
                tiles[tmpJ] = 0
                if (transitions != null) {
                    transitions!!.add(Transition(Actions.SLIDE, val2, tmpJ, tmpI))
                    transitions!!.add(Transition(Actions.BLANK, BLANK, tmpJ))
                }
                val1 = 0
                tmpI = tmpJ
                moved = true
            } else if (val1 != 0 && val2 == 0) {
                val1 = 0
                tmpI = tmpJ
            }
        }
        return moved
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

    private fun compactTileRowOrColumn(index1: Int, index2: Int, index3: Int, index4: Int): Boolean {

        var compacted = false

        for (j in 1..COL_CNT) {
            var val1 = 0
            var val2 = 0
            var tmpI = 0
            var tmpJ = 0
            when (j) {
                1 -> {
                    val1 = tiles[index1]
                    val2 = tiles[index2]
                    tmpI = index1
                    tmpJ = index2
                }
                2 -> {
                    val1 = tiles[index2]
                    val2 = tiles[index3]
                    tmpI = index2
                    tmpJ = index3
                }
                3 -> {
                    val1 = tiles[index3]
                    val2 = tiles[index4]
                    tmpI = index3
                    tmpJ = index4
                }
                else -> {
                }
            }

            if (val1 != 0 && val1 == val2) {
                tiles[tmpI] = val1 * 2
                score += tiles[tmpI]
                if (tiles[tmpI] > maxTile) maxTile = tiles[tmpI]
                numEmpty++
                tiles[tmpJ] = 0
                compacted = true
                if (transitions != null) {
                    transitions!!.add(Transition(Actions.COMPACT, tiles[tmpI], tmpJ, tmpI))
                    transitions!!.add(Transition(Actions.BLANK, BLANK, tmpJ))
                }
            }
        }
        return compacted
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

    fun actionMoveLeft(): Boolean {
        val a = slideLeft()
        val b = compactLeft()
        val c = slideLeft()
        return a || b || c
    }

    fun actionMoveRight(): Boolean {
        val a = slideRight()
        val b = compactRight()
        val c = slideRight()
        return a || b || c
    }

    fun actionMoveUp(): Boolean {
        val a = slideUp()
        val b = compactUp()
        val c = slideUp()
        return a || b || c
    }

    fun actionMoveDown(): Boolean {
        val a = slideDown()
        val b = compactDown()
        val c = slideDown()
        return a || b || c
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
        private var posStart = -1
        var posFinal = -1

        constructor(action: Actions, value: Int, posStart: Int, posFinal: Int) {
            type = action
            this.value = value
            this.posStart = posStart
            this.posFinal = posFinal
        }

        constructor(action: Actions, value: Int, posFinal: Int) {
            type = action
            this.value = value
            this.posFinal = posFinal
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
