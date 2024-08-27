package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.PrimaryColor

@Composable
fun CustomFloatActionButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = PrimaryColor,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomFloatActionButtonPreview() {
    ConfectionaryAdminTheme {
        Column(
            modifier = Modifier.height(300.dp).width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            CustomFloatActionButton(
                icon = Icons.Default.AddCircle
            ) {}
            CustomFloatActionButton(
                icon = Icons.AutoMirrored.Filled.Notes
            ) {}
            CustomFloatActionButton(
                icon = Icons.Default.PersonAdd
            ) {}
        }
    }
}