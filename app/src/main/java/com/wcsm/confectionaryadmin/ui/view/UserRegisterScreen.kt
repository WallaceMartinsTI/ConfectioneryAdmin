package com.wcsm.confectionaryadmin.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import com.wcsm.confectionaryadmin.ui.components.CustomTextField
import com.wcsm.confectionaryadmin.ui.components.CustomTopAppBar
import com.wcsm.confectionaryadmin.ui.components.PrimaryButton
import com.wcsm.confectionaryadmin.ui.components.ScreenDescription
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.InterFontFamily
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.viewmodel.UserRegisterViewModel

@Composable
fun UserRegisterScreen(
    navController: NavController,
    userRegisterViewModel: UserRegisterViewModel = hiltViewModel()
) {
    val userRegisterState by userRegisterViewModel.userRegisterState.collectAsState()

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    val focusRequester = remember { List(2) { FocusRequester() } }

    LaunchedEffect(userRegisterState) {
        if(userRegisterState.isRegistered) {
            navController.navigate(Screen.Login.route)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = stringResource(id = R.string.screen_title_user_register_screen),
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenDescription(
                description = stringResource(id = R.string.user_register_screen_description),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id = R.string.user_register_screen_message),
                color = Primary,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_name),
                placeholder = stringResource(id = R.string.textfield_placeholder_name),
                modifier = Modifier
                    .padding(top = 24.dp)
                    .focusRequester(focusRequester[0]),
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                errorMessage = userRegisterState.nameErrorMessage,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                   IconButton(
                       onClick = {
                           userRegisterViewModel.updateUserRegisterState(
                               userRegisterState.copy(
                                   name = ""
                               )
                           )
                           focusRequester[0].requestFocus()
                       },
                       modifier = Modifier.focusProperties { canFocus = false }
                   ) {
                       if(userRegisterState.name.isNotEmpty()) {
                           Icon(
                               imageVector = Icons.Default.Clear,
                               contentDescription = null
                           )
                       }
                   }
                },
                value = userRegisterState.name
            ) {
                userRegisterViewModel.updateUserRegisterState(
                    userRegisterState.copy(
                        name = it
                    )
                )
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_email),
                placeholder = stringResource(id = R.string.textfield_placeholder_email),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .focusRequester(focusRequester[1]),
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                errorMessage = userRegisterState.emailErrorMessage,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            userRegisterViewModel.updateUserRegisterState(
                                userRegisterState.copy(
                                    email = ""
                                )
                            )
                            focusRequester[1].requestFocus()
                        },
                        modifier = Modifier.focusProperties { canFocus = false }
                    ) {
                        if(userRegisterState.email.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                },
                value = userRegisterState.email,
            ) {
                userRegisterViewModel.updateUserRegisterState(
                    userRegisterState.copy(
                        email = it
                    )
                )
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_password),
                placeholder = stringResource(id = R.string.textfield_placeholder_password),
                modifier = Modifier.padding(top = 8.dp),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                errorMessage = userRegisterState.passwordErrorMessage,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword },
                        modifier = Modifier.focusProperties { canFocus = false }
                    ) {
                        Icon(
                            imageVector = if(showPassword) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if(showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                value = userRegisterState.password,
            ) {
                userRegisterViewModel.updateUserRegisterState(
                    userRegisterState.copy(
                        password = it
                    )
                )
            }

            CustomTextField(
                label = stringResource(id = R.string.textfield_label_confirm_password),
                placeholder = stringResource(id = R.string.textfield_placeholder_confirm_password),
                modifier = Modifier.padding(top = 8.dp),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                errorMessage = userRegisterState.confirmPasswordErrorMessage,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { showConfirmPassword = !showConfirmPassword },
                        modifier = Modifier.focusProperties { canFocus = false }
                    ) {
                        Icon(
                            imageVector = if(showConfirmPassword) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if(showConfirmPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                value = userRegisterState.confirmPassword,
            ) {
                userRegisterViewModel.updateUserRegisterState(
                    userRegisterState.copy(
                        confirmPassword = it
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(text = stringResource(id = R.string.btn_text_user_register)) {
                userRegisterViewModel.registerNewUser()
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}

@Preview
@Composable
private fun UserRegisterScreenPreview() {
    ConfectionaryAdminTheme {
        val navController = rememberNavController()
        UserRegisterScreen(navController)
    }
}