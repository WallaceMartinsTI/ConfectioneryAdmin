package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.InvertedAppBackgroundColor
import com.wcsm.confectionaryadmin.ui.theme.LightRedColor
import com.wcsm.confectionaryadmin.ui.theme.PrimaryColor

@Composable
fun DialogConfirmDeleteUser(
    title: String,
    message: String,
    onConfirmText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(InvertedAppBackgroundColor)
                .border(1.dp, PrimaryColor, RoundedCornerShape(15.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                color = PrimaryColor,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            Text(
                text = message,
                color = Color.White,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Justify
            )

            CustomDialogButton(
                text = onConfirmText.uppercase(),
                width = 290.dp,
                color = LightRedColor
            ) {
                onConfirm()
                onDismiss()
            }

            CustomDialogButton(
                text = "CANCELAR",
                width = 290.dp,
                color = Color.Black
            ) {
                onDismiss()
            }
        }
    }
}

@Preview
@Composable
private fun ConfirmDeleteUserDialogPreview() {
    ConfectionaryAdminTheme {
        DialogConfirmDeleteUser(
            title = "Deletar Usuário",
            message = "Tem certeza que deseja deletar sua conta de usuário?",
            onConfirmText = "Deletar Usuário",
            onDismiss = {},
            onConfirm = {}
        )
    }
}