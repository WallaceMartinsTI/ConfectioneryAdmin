package com.wcsm.confectionaryadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfectionaryAdminTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Greeting("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name! - Fonte Normal",
            fontFamily = FontFamily.Default,
            modifier = modifier,
        )

        Text(
            text = "Hello $name! - Fonte InterFontFamily",
            fontFamily = InterFontFamily,
            modifier = modifier,
        )

        Text(
            text = "Hello $name! - Fonte InterFontFamily com Weight Normal",
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            modifier = modifier
        )

        Text(
            text = "Hello $name! - Fonte Normal com Style Italic",
            fontFamily = FontFamily.Default,
            fontStyle = FontStyle.Italic,
            modifier = modifier
        )

        Text(
            text = "Hello $name! - Fonte Inter com Style Italic",
            fontFamily = InterFontFamily,
            fontStyle = FontStyle.Italic,
            modifier = modifier
        )

        Text(
            text = "Hello $name! - Fonte Inter com Weight Bold",
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )

        Text(
            text = "Hello $name! - Fonte Inter com Weight Semi-Bold",
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConfectionaryAdminTheme {
        Greeting("Android")
    }
}