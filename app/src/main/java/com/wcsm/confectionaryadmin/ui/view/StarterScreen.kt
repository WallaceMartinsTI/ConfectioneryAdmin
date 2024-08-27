package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.navigation.Screen
import com.wcsm.confectionaryadmin.ui.components.AppTitle
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackgroundColor
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.PrimaryColor

@Composable
fun StarterScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTitle(modifier = Modifier.padding(top = 24.dp))

        ScreenDescription(
            description = stringResource(id = R.string.starter_screen_description),
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Text(
            text = stringResource(id = R.string.starter_screen_welcome_message),
            color = Color.White,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 30.dp)
        )

        Text(
            text = stringResource(id = R.string.starter_screen_message),
            color = PrimaryColor,
            fontFamily = InterFontFamily,
            fontSize = 18.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .width(220.dp)
                .padding(top = 24.dp)
        )

        Text(
            text = stringResource(id = R.string.starter_screen_start_helper),
            color = Color.White,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(290.dp)
                .padding(top = 120.dp)
        )

        PrimaryButton(
            text = stringResource(id = R.string.btn_text_start),
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        ) {
            navController.navigate(Screen.Login.route)
        }


    }
}

@Preview
@Composable
private fun StarterScreenPreview() {
    ConfectionaryAdminTheme {
        val navController = rememberNavController()
        StarterScreen(navController = navController)
    }
}