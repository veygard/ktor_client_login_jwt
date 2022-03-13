package com.example.composetest.login.presentation.supports.visualTransformation.login

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return phoneNumFilter(text)
    }
}
fun phoneNumFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
    var outRaw = ""
    for (i in trimmed.indices) {
        if (i==0) outRaw += "+"
        if (i==1) outRaw += "("
        outRaw += trimmed[i]
        if (i==3) outRaw +=") "
        if (i==6 || i==8 ) outRaw += " "
    }
    val template = "+7(___) ___ __ __"
    val length = outRaw.length
    val templateByLength = template.substring(length,template.length)
    val out = outRaw + templateByLength


    val phoneNumOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 0) return offset+4
            if (offset <= 1) return offset +4
            if (offset <= 2) return offset +3
            if (offset <= 3) return offset +3
            if (offset <= 7) return offset +4
            if (offset <= 9) return offset +5
            if (offset <= 11) return offset +6
            return 17
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=0) return offset
            if (offset <=2) return offset -1
            if (offset <=7) return offset -2
            if (offset <=12) return offset -4
            if (offset <=15) return offset -5
            if (offset <=18) return offset -6
            return 11
        }
    }
    return TransformedText(AnnotatedString(out), phoneNumOffsetTranslator)
}