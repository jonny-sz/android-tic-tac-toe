package com.jonnydev.tictactoe.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:text")
fun convertCharToString(view: TextView, value: Char) {
    view.text = value.toString()
}
