package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.ui.theme.ButtonBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    width: Dp = 290.dp,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .width(width)
            .clip(RoundedCornerShape(15.dp))
            .background(ButtonBackground)
            .border(
                width = 1.dp,
                color = Primary,
                shape = RoundedCornerShape(15.dp)
            )
            .height(50.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Primary,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    ConfectionaryAdminTheme {
        Column(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton("INICIAR") {}
        }
    }
}