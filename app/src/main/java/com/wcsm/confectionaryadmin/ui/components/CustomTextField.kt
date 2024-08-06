package com.wcsm.confectionaryadmin.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.LightRed
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.theme.TextFieldBackground

@Composable
fun CustomTextField(
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    width: Dp = 280.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String?,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    value: String,
    onValueChange: (newValue: String) -> Unit
) {
    val isError = errorMessage != null

    val colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = TextFieldBackground,
        unfocusedContainerColor = TextFieldBackground,
        errorContainerColor = TextFieldBackground,
        cursorColor = Primary,
        focusedBorderColor = Primary,
        unfocusedBorderColor = Primary,
        selectionColors = TextSelectionColors(
            Primary, Color.Transparent
        )
    )

    OutlinedTextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        modifier = modifier.width(width),
        label = {
            Text(
                text = label,
                color = Color.White,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                color = Color.White,
                fontFamily = InterFontFamily,
                fontStyle = FontStyle.Italic
            )
        },
        textStyle = TextStyle(
            color = Primary,
            fontFamily = InterFontFamily,
            fontSize = 18.sp
        ),
        colors = colors,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        isError = isError,
    )

    if(errorMessage != null) {
        CustomErrorMessage(errorMessage = errorMessage)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    ConfectionaryAdminTheme {
        var email by remember {mutableStateOf("") }
        var password by remember {mutableStateOf("") }

        Column(
            modifier = Modifier
                .width(500.dp)
                .height(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomTextField(
                label = "EMAIL",
                placeholder = "Digite seu email",
                imeAction = ImeAction.Next,
                errorMessage = null,
                value = email,
                onValueChange = { newValue -> email = newValue  }
            )

            CustomTextField(
                label = "SENHA",
                placeholder = "Digite sua senha",
                imeAction = ImeAction.Done,
                errorMessage = "Você deve digitar sua senha.",
                value = password,
                onValueChange = { newValue -> password = newValue  }
            )
        }
    }
}