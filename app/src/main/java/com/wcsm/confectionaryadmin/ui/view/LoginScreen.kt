package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.components.AppTitle
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var showPassword by remember { mutableStateOf(false) }

        val focusRequester = remember { FocusRequester() }

        AppTitle(modifier = Modifier.padding(top = 24.dp))

        ScreenDescription(
            description = stringResource(id = R.string.login_screen_description),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = stringResource(id = R.string.login_screen_message),
            color = Primary,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(220.dp)
        )

        CustomTextField(
            label = stringResource(id = R.string.textfield_label_email),
            placeholder = stringResource(id = R.string.textfield_placeholder_email),
            modifier = Modifier.padding(top = 24.dp).focusRequester(focusRequester),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        email = ""
                        focusRequester.requestFocus()
                    },
                    modifier = Modifier.focusProperties { canFocus = false }
                ) {
                    if(email.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            },
            errorMessage = null,
            value = email,
        ) {
            email = it
        }

        CustomTextField(
            label = stringResource(id = R.string.textfield_label_password),
            placeholder = stringResource(id = R.string.textfield_placeholder_password),
            modifier = Modifier.padding(top = 16.dp),
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { showPassword = !showPassword }
                ) {
                    Icon(
                        imageVector =
                        if(showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            errorMessage = null,
            visualTransformation =
            if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            value = password,
        ) {
            password = it
        }

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(text = stringResource(id = R.string.btn_text_login)) {
            // Login
            navController.navigate(Screen.NavigationHolder.route)
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(text = stringResource(id = R.string.btn_text_create_account)) {
            navController.navigate(Screen.UserRegister.route)
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ConfectionaryAdminTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}