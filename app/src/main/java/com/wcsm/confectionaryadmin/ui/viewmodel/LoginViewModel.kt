package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.wcsm.confectionaryadmin.data.model.states.LoginState
import com.wcsm.confectionaryadmin.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val TAG = "#FIREBASE_AUTH#USER_LOGIN#"

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun updateLoginState(newState: LoginState) {
        _loginState.value = newState
    }

    fun signIn() {
        val newState = _loginState.value.copy(
            emailErrorMessage = null,
            passwordErrorMessage = null
        )
        updateLoginState(newState)

        val email = loginState.value.email
        val password = loginState.value.password
        if(!validateUserEmail(email) || !validateUserPassword(password)) {
            return
        }

        _loginState.value = loginState.value.copy(isLoading = true)

        viewModelScope.launch {
            userRepository.signIn(email, password)
                .addOnSuccessListener {
                    Log.i(TAG, "Usuário LOGADO com SUCESSO!")
                    _loginState.value = loginState.value.copy(isLogged = true)
                    _loginState.value = loginState.value.copy(isLoading = false)
                }
                .addOnFailureListener {
                    Log.i(TAG, "ERRO ao LOGAR USUÁRIO.")
                    val errorMessage = try {
                        throw it
                    } catch (invalidUser: FirebaseAuthInvalidUserException) {
                        invalidUser.printStackTrace()
                        "E-mail não cadastrado."
                    } catch (invalidCredentials: FirebaseAuthInvalidCredentialsException) {
                        invalidCredentials.printStackTrace()
                        "E-mail ou senha estão incorretos."
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                        "Erro inesperado. Tente novamente mais tarde."
                    }

                    updateLoginState(
                        newState.copy(
                            emailErrorMessage = if(
                                it is FirebaseAuthInvalidCredentialsException ||
                                it is FirebaseAuthInvalidUserException
                            ) errorMessage else null,
                            isLoading = false
                        )
                    )
                }
        }
    }

    private fun validateUserEmail(email: String): Boolean {
        return if(email.isEmpty()) {
            updateLoginState(loginState.value.copy(emailErrorMessage = "Informe o seu e-mail."))
            false
        } else {
            true
        }
    }

    private fun validateUserPassword(password: String): Boolean {
        return if(password.isEmpty()) {
            updateLoginState(loginState.value.copy(passwordErrorMessage = "Informe a sua senha."))
            false
        } else {
            true
        }
    }
}
