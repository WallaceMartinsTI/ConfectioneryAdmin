package com.wcsm.confectionaryadmin.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.DarkGreen
import com.wcsm.confectionaryadmin.ui.theme.InProductionStatus
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.LightRed
import com.wcsm.confectionaryadmin.ui.theme.Primary

@Composable
fun SyncDialog(
    isSendingFromRoomToFirestore: Boolean,
    onDontShowAgain: (Boolean) -> Unit,
    onDissmiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val sendDataFromRoomToFirestoreText = buildAnnotatedString {
        append("Você está prestes a substituir os dados na nuvem pelos dados locais. Essa ação apagará permanentemente as informações já armazenadas na nuvem. Caso deseje recuperar os dados anteriores, acesse ")
        withStyle(style = SpanStyle(color = InProductionStatus, fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline)) {
            append("Info > Sincronizar")
        }
        append(" antes de prosseguir. Se a nuvem não contiver dados, você pode sincronizar sem problemas.")
    }

    val sendDataFromFirestoreToRoomText = buildAnnotatedString {
        append("Você está prestes a substituir os dados locais pelos dados da nuvem. Essa ação apagará permanentemente as informações armazenadas localmente. Certifique-se de que deseja continuar antes de prosseguir.")
    }

    var agreementAccept by rememberSaveable { mutableStateOf(false) }
    var dontShowAlertAgain by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onDissmiss()
        }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(brush = AppBackground)
                .border(1.dp, Primary, RoundedCornerShape(15.dp))
                .padding(16.dp)
        ) {
            Text(
                text = if(isSendingFromRoomToFirestore) sendDataFromRoomToFirestoreText
                else sendDataFromFirestoreToRoomText,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Justify
            )

            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = agreementAccept,
                    onCheckedChange = { agreementAccept = !agreementAccept },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Primary,
                        checkmarkColor = Color.White,
                        uncheckedColor = Primary,
                    )
                )
                Text(
                    text = "Li e estou de acordo.",
                    color = Primary,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            if(agreementAccept) {
                HorizontalDivider(color = Color.White)
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = dontShowAlertAgain,
                        onCheckedChange = { dontShowAlertAgain = !dontShowAlertAgain },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Primary,
                            checkmarkColor = Color.White,
                            uncheckedColor = Primary,
                        )
                    )
                    Text(
                        text = "Não mostrar novamente.",
                        color = Primary,
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomDialogButton(
                        text = "CONFIRMAR",
                        width = 250.dp,
                        color = DarkGreen
                    ) {
                        if(dontShowAlertAgain) {
                            onDontShowAgain(true)
                        } else {
                            onDontShowAgain(false)
                        }
                        onConfirm()
                        onDissmiss()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomDialogButton(
                        text = "CANCELAR",
                        width = 250.dp,
                        color = LightRed
                    ) {
                        onDissmiss()
                    }
                }
            }
        }
    }
}

@Preview(name = "Sync UP")
@Composable
private fun SyncDialogPreviewSyncUp() {
    ConfectionaryAdminTheme {
        SyncDialog(
            isSendingFromRoomToFirestore = true,
            onDontShowAgain = {},
            onDissmiss = {},
            onConfirm = {}
        )
    }
}

@Preview(name = "Sync DOWN")
@Composable
private fun SyncDialogPreviewSyncDown() {
    ConfectionaryAdminTheme {
        SyncDialog(
            isSendingFromRoomToFirestore = false,
            onDontShowAgain = {},
            onDissmiss = {},
            onConfirm = {}
        )
    }
}

