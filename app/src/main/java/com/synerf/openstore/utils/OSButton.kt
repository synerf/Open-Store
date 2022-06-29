package com.synerf.openstore.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class OSButton(context: Context, attributeSet: AttributeSet): AppCompatButton(context, attributeSet) {

    init {
        applyFont()
    }

    private fun applyFont() {
        // get font file from asset folder and set it to the title textview
        val typeFace: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")

        setTypeface(typeFace)
    }
}