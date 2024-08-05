package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme

@Composable
fun CustomersScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .background(AppBackground)
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "CUSTOMERS SCREEN")
    }
}

@Preview(showBackground = true)
@Composable
fun CustomersScreenPreview() {
    ConfectionaryAdminTheme {
        val paddingValues = PaddingValues()
        CustomersScreen(paddingValues)
    }
}