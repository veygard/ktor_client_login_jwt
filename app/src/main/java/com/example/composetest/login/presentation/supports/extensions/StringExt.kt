package com.example.composetest.login.presentation.supports.extensions

import com.example.composetest.login.presentation.supports.text_validation.PhoneMask
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString

fun String.formatPhone(mask: String = PhoneMask.MASK_1): String {
    val result = Mask(mask)
        .apply(
            CaretString(
                string = this,
                caretPosition = this.length,
                caretGravity = CaretString.CaretGravity.FORWARD(false)
            )
        )
    return result.formattedText.string
}

fun String.formattedPhoneIsValid(
    mask: String = PhoneMask.MASK_1,
    length: Int = 12
): Boolean {
    val result = Mask(mask)
        .apply(
            CaretString(
                string = this,
                caretPosition = this.length,
                caretGravity = CaretString.CaretGravity.FORWARD(false)
            )
        )
    return result.formattedText.string.length == length
}
