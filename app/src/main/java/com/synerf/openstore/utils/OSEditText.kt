package com.synerf.openstore.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class OSEditText(context: Context, attributeSet: AttributeSet): AppCompatEditText(context, attributeSet) {

    init {
        applyFont()
    }

    // function to apply font
    private fun applyFont() {
        // get font file from asset folder and set it to the title textview
        val typeFace: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")

        setTypeface(typeFace)
    }
}