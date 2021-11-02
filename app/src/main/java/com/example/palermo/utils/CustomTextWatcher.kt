package com.example.palermo.utils

import android.text.Editable
import android.text.TextWatcher

abstract class CustomTextWatcher() : TextWatcher {
    var currentIndex: Int = -1
    fun updatePosition(pos: Int) {
        currentIndex = pos
    }

    override fun afterTextChanged(p0: Editable?) { textChanged() }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    abstract fun textChanged()
}