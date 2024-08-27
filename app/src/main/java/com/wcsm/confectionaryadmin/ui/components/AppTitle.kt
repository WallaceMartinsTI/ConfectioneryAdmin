package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.ui.theme.AppTitleGradientColor
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily

@Composable
fun AppTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.app_name_formatted),
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        textAlign = TextAlign.Center,
        style = TextStyle(
            brush = AppTitleGradientColor
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTitlePreview() {
    ConfectionaryAdminTheme {
        AppTitle()
    }
}