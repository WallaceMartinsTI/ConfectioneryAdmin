package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.PrimaryColor

@Composable
fun CustomLoading(
    size: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            color = PrimaryColor,
        )
    }
}

@Preview
@Composable
private fun CustomLoadingPreview() {
    ConfectionaryAdminTheme {
        CustomLoading(size = 32.dp)
    }
}