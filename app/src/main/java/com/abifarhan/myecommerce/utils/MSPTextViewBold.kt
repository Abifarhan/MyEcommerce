package com.abifarhan.myecommerce.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

class MSPTextViewBold(context: Context, attributeSet: AttributeSet) :
    AppCompatTextView(context, attributeSet) {

        init {
            applyFont()
        }

    private fun applyFont() {
        val typeFace: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        typeface = typeFace
    }
}