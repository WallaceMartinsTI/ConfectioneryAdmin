package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.ScreenDescriptionColor

@Composable
fun ScreenDescription(description: String, modifier: Modifier = Modifier) {
    Text(
        text = description,
        color = ScreenDescriptionColor,
        fontFamily = InterFontFamily,
        fontStyle = FontStyle.Italic,
        fontSize = 24.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenDescriptionPreview() {
    ConfectionaryAdminTheme {
        ScreenDescription("Painel de Administrador")
    }
}