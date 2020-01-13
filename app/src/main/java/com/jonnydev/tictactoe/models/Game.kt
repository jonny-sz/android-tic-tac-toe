package com.jonnydev.tictactoe.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jonnydev.tictactoe.BR

class Game() : BaseObservable() {
    val player1 = Player("Player1", 'O')
    val player2 = Player("Player2", 'X')
    @get:Bindable
    var activePlayer = player1
        set(value) {
            field = value
            notifyPropertyChanged(BR.activePlayer)
        }
    @get:Bindable
    var hasWinner = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasWinner)
        }

    fun switchPlayers() {
        activePlayer = if (activePlayer == player1) player2 else player1
    }

    fun restart() {
        activePlayer = player1
        hasWinner = false
    }

    fun swapSymb() {
        val tempSymb = player1.symb
        player1.symb = player2.symb
        player2.symb = tempSymb
    }
}
