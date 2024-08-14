package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.DarkGreen
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.LightRed

@Composable
fun CustomDialogButton(
    text: String,
    width: Dp,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .width(width)
            .clickable { onClick() }
            .border(1.dp, color, RoundedCornerShape(15.dp))
            .padding(vertical = 8.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = color,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun ChangeStatusDialogButtonPreview() {
    ConfectionaryAdminTheme {
        Column {
            CustomDialogButton(text = "SIM", color = DarkGreen, width = 100.dp) {}
            Spacer(modifier = Modifier.height(8.dp))
            CustomDialogButton(text = "NÃO", color = LightRed, width = 100.dp) {}
        }
    }
}