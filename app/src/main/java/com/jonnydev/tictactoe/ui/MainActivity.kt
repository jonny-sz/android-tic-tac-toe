package com.jonnydev.tictactoe.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import com.jonnydev.tictactoe.R
import com.jonnydev.tictactoe.databinding.ActivityMainBinding
import com.jonnydev.tictactoe.models.CellView
import com.jonnydev.tictactoe.models.Game
import com.jonnydev.tictactoe.toast
import kotlinx.android.synthetic.main.activity_main.*

const val SIZE = 3
const val LAST = SIZE - 1
const val CELL_AMOUNT = SIZE * SIZE

const val URL = "http://jonny-dev.com"

class MainActivity : AppCompatActivity() {

    private var filledCellsCount = 0
    private lateinit var field: Array<Array<CellView>>
    private lateinit var winnerLine: Array<CellView>
    private lateinit var game: Game
    private lateinit var binding: ActivityMainBinding

    private lateinit var customTabsIntent: CustomTabsIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        game = Game()
        binding.game = game
        field = Array(SIZE) { Array(SIZE) { CellView(this) } }
        winnerLine = Array(SIZE) { CellView(this) }
        fillGameField()
        customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .build()

        btn_reset.setOnClickListener { reset() }
        btn_swap.setOnClickListener { swapSymb() }
        btn_launcher.setOnClickListener { customTabsIntent.launchUrl(this, Uri.parse(URL)) }
    }

    private fun fillGameField() {
        for (row in 0..LAST) {
            val tableRow = TableRow(this)

            for (col in 0..LAST) {
                val cell = field[row][col]

                cell.setOnClickListener { view ->
                    onCellClick(view, row, col)
                }

                val cellParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT
                )
                TextViewCompat.setAutoSizeTextTypeWithDefaults(
                    cell, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
                )
                tableRow.addView(cell, cellParams)
            }

            field_table.addView(
                tableRow,
                TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

    private fun onCellClick(view: View?, row: Int, col: Int) {
        view?.let {
            val cell = it as CellView

            if (!game.hasWinner && !cell.isFilled) {
                val symb = game.activePlayer.symb
                val isWin: Boolean

                cell.symb = symb
                filledCellsCount++
                isWin = isLineFilled(symb, row, col) || isDiagonalFilled(symb)

                when {
                    isWin -> onWinGame()
                    filledCellsCount == CELL_AMOUNT -> toast("GAME OVER")
                    else -> game.switchPlayers()
                }
            }
        }
    }

    private fun onWinGame() {
        changeWinnerCellColor(Color.RED)
        drawWinnerLine()
        toast("${game.activePlayer.name} WON!!!")
        game.hasWinner = true
    }

    private fun drawWinnerLine() {
        val offset = winnerLine[0].measuredHeight / 2

        val startX = winnerLine[0].x + offset
        val startY = (winnerLine[0].parent as TableRow).y + offset
        val stopX = winnerLine[2].x + offset
        val stopY = (winnerLine[2].parent as TableRow).y + offset

        line_view.drawWinnerLine(startX, startY, stopX, stopY)
    }

    private fun isLineFilled(symb: Char, row: Int, col: Int): Boolean {
        val line = 0..LAST

        return (isRowFilled(line, row, symb) || isColFilled(line, col, symb))
    }

    private fun isColFilled(line: IntRange, col: Int, symb: Char): Boolean {
        for (row in line) {
            val cell = field[row][col]

            if (cell.symb != symb) return false

            winnerLine[row] = cell
        }
        return true
    }

    private fun isRowFilled(line: IntRange, row: Int, symb: Char): Boolean {
        for (col in line) {
            val cell = field[row][col]

            if (cell.symb != symb) return false

            winnerLine[col] = cell
        }
        return true
    }

    private fun isDiagonalFilled(symb: Char) = (isToRightFilled(symb) || isToLeftFilled(symb))

    private fun isToRightFilled(symb: Char): Boolean {
        for (i in 0..LAST) {
            val cell = field[i][i]

            if (cell.symb != symb) return false

            winnerLine[i] = cell
        }
        return true
    }

    private fun isToLeftFilled(symb: Char): Boolean {
        for ((row, col) in (LAST downTo 0).withIndex()) {
            val cell = field[row][col]

            if (cell.symb != symb) return false

            winnerLine[row] = cell
        }
        return true
    }

    private fun changeWinnerCellColor(color: Int) {
        for (cell in winnerLine) {
            cell.setTextColor(color)
        }
    }

    private fun swapSymb() {
        game.swapSymb()
        reset()
    }

    private fun reset() {
        line_view.removeWinnerLine()
        filledCellsCount = 0
        game.restart()

        for (row in field) {
            for (cell in row) {
                cell.reset()
            }
        }

        changeWinnerCellColor(Color.BLACK)
    }
}
