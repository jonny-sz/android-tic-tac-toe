package com.jonnydev.tictactoe.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jonnydev.tictactoe.BR

class Player (_name: String, _symb: Char) : BaseObservable() {
    @get:Bindable
    var name = _name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var symb = _symb
        set(value) {
            field = value
            notifyPropertyChanged(BR.symb)
        }
}
