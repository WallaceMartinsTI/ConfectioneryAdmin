package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.wcsm.confectionaryadmin.ui.theme.AppBackgroundColor
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.PrimaryColor
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePicker(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    selectorColor = PrimaryColor,
                    timeSelectorSelectedContainerColor = PrimaryColor.copy(alpha = 0.75f),
                    timeSelectorSelectedContentColor = Color.White
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(PrimaryColor)
                    .padding(12.dp)
                    .clickable {
                        val selectedHour = timePickerState.hour
                        val selectedMinute = timePickerState.minute
                        val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                        onConfirm(formattedTime)
                    }
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ESCOLHER",
                    color = Color.White,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomTimePickerPreview() {
    ConfectionaryAdminTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var showDial by remember { mutableStateOf(true) }
            var selectedTime by remember { mutableStateOf("No time selected") }

            if(showDial) {
                CustomTimePicker(
                    onDismiss = { showDial = false }
                ) { time ->
                    selectedTime = time
                    showDial = false
                }
            }

            Text(text = "Abrir DIALOG", modifier = Modifier.clickable { showDial = true })
            Text(text = "selectedTime: $selectedTime")
        }
    }
}