package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradient
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily

@Preview(showBackground = true)
@Composable
fun AppTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = "CONFECTIONARY\nADMIN",
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        textAlign = TextAlign.Center,
        style = TextStyle(
            brush = AppTitleGradient
        ),
        modifier = modifier
    )
}