package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.R
import com.wcsm.confectionaryadmin.data.model.navigation.Screen
import com.wcsm.confectionaryadmin.ui.components.AppTitle
import com.wcsm.confectionaryadmin.ui.components.CustomErrorMessage
import com.wcsm.confectionaryadmin.ui.components.CustomLoading
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by loginViewModel.loginState.collectAsState()
    val isConnectedd by loginViewModel.isConnected.collectAsState()
    val saveLogin by loginViewModel.saveLogin.collectAsState()

    var errorMessage by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(loginState) {
        if(loginState.isLogged) {
            navController.navigate(Screen.NavigationHolder.route)
        }
    }

    LaunchedEffect(saveLogin) {
        if(!saveLogin) {
            loginViewModel.clearLoggedUser()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            modifier = Modifier
                .padding(top = 24.dp)
                .focusRequester(focusRequester),
            keyboardType = KeyboardType.Email,
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
                        loginViewModel.updateLoginState(
                            loginState.copy(
                                email = ""
                            )
                        )
                        focusRequester.requestFocus()
                    },
                    modifier = Modifier.focusProperties { canFocus = false }
                ) {
                    if(loginState.email.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            },
            errorMessage = loginState.emailErrorMessage,
            value = loginState.email,
        ) {
            loginViewModel.updateLoginState(
                loginState.copy(
                    email = it
                )
            )
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
            errorMessage = loginState.passwordErrorMessage,
            visualTransformation =
            if(showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            value = loginState.password,
        ) {
            loginViewModel.updateLoginState(
                loginState.copy(
                    password = it
                )
            )
        }

        Row(
            modifier = Modifier.width(290.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Salvar login?",
                color = Primary,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Checkbox(
                checked = saveLogin,
                onCheckedChange = {
                    loginViewModel.updateSaveLogin(!saveLogin)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary,
                    checkmarkColor = Color.White,
                    uncheckedColor = Primary,
                )
            )
        }

        HorizontalDivider(
            color = Color.White,
            modifier = Modifier
                .width(290.dp)
                .padding(top = 8.dp, bottom = 40.dp)
        )

        Column(
            modifier = Modifier.width(290.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(loginState.isLoading) {
                CustomLoading(size = 80.dp)
            } else {
                PrimaryButton(text = stringResource(id = R.string.btn_text_login)) {
                    errorMessage = ""
                    loginViewModel.checkConnection()
                    if(isConnectedd) {
                        loginViewModel.signIn()
                    } else {
                        // SE NAO TIVER USUARIO SALVO
                        errorMessage = "Sem conexão no momento, tente mais tarde"
                    }
                }

                if(errorMessage.isNotEmpty()) {
                    CustomErrorMessage(
                        errorMessage = errorMessage,
                        errorIcon = {
                            Icon(
                                imageVector = Icons.Filled.WifiOff,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryButton(text = stringResource(id = R.string.btn_text_create_account)) {
                    navController.navigate(Screen.UserRegister.route)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
private fun LoginScreenPreview(loginViewModel: LoginViewModel = hiltViewModel()) {
    ConfectionaryAdminTheme {
        val navController = rememberNavController()
        LoginScreen(navController, loginViewModel)
    }
}