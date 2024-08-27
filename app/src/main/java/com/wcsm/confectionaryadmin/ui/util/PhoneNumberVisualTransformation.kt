package com.wcsm.confectionaryadmin.ui.util

import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val rawText = text.text.replace("[^\\d]".toRegex(), "") // Remove non-numeric characteres

        val formattedText = when {
            rawText.isEmpty() -> ""
            rawText.length <= 2 -> "(${rawText}"
            rawText.length <= 6 -> "(${rawText.substring(0, 2)}) ${rawText.substring(2)}"
            rawText.length <= 10 -> "(${rawText.substring(0, 2)}) ${rawText.substring(2, 7)}-${rawText.substring(7)}"
            else -> "(${rawText.substring(0, 2)}) ${rawText.substring(2, 7)}-${rawText.substring(7, 11)}"
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = PhoneNumberOffsetMapping(
                originalText = text.text,
                transformedText = formattedText
            )
        )
    }
}

class PhoneNumberOffsetMapping(
    private val originalText: String,
    private val transformedText: String
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        val sanitizedOriginal = originalText.take(offset).replace("[^\\d]".toRegex(), "")
        val transformedOffset = when {
            sanitizedOriginal.isEmpty() -> 0
            sanitizedOriginal.length <= 2 -> sanitizedOriginal.length + 1
            sanitizedOriginal.length <= 6 -> sanitizedOriginal.length + 3
            sanitizedOriginal.length <= 10 -> sanitizedOriginal.length + 4
            else -> transformedText.length
        }

        return transformedOffset.coerceIn(0, transformedText.length)
    }

    override fun transformedToOriginal(offset: Int): Int {
        val sanitizedTransformed = transformedText.take(offset).replace("[^\\d]".toRegex(), "")
        val originalOffset = when {
            sanitizedTransformed.isEmpty() -> 0
            sanitizedTransformed.length <= 2 -> sanitizedTransformed.length
            sanitizedTransformed.length <= 6 -> sanitizedTransformed.length - 1
            sanitizedTransformed.length <= 10 -> sanitizedTransformed.length - 2
            else -> sanitizedTransformed.length - 3
        }

        return originalOffset.coerceIn(0, originalText.length)
    }
}