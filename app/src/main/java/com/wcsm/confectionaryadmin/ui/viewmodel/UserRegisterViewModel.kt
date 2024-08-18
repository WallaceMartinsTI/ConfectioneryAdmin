package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.wcsm.confectionaryadmin.data.model.entities.User
import com.wcsm.confectionaryadmin.data.model.states.UserRegisterState
import com.wcsm.confectionaryadmin.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val TAG = "#FIREBASE_AUTH#USER_REGISTER#"

    private val _userRegisterState = MutableStateFlow(UserRegisterState())
    val userRegisterState = _userRegisterState.asStateFlow()

    fun updateUserRegisterState(newState: UserRegisterState) {
        _userRegisterState.value = newState
    }

    fun registerNewUser() {
        val newState = _userRegisterState.value.copy(
            nameErrorMessage = null,
            emailErrorMessage = null,
            passwordErrorMessage = null,
            confirmPasswordErrorMessage = null
        )
        updateUserRegisterState(newState)

        val name = userRegisterState.value.name
        val email = userRegisterState.value.email
        val password = userRegisterState.value.password
        val confirmPassword = userRegisterState.value.confirmPassword

        if(isAllFieldsValid(name, email, password, confirmPassword)) {
            registerUserFirebase(name, email, password)
        }
    }

    private fun isAllFieldsValid(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        val isNameValid = validateName(name)
        val isEmailValid = validateEmail(email)
        val isPasswordValid = validatePassword(password, confirmPassword)

        return isNameValid && isEmailValid && isPasswordValid
    }

    private fun validateName(name: String): Boolean {
        val newState = _userRegisterState.value.copy(
            nameErrorMessage = null
        )
        updateUserRegisterState(newState)

        return if(name.isEmpty()) {
            updateUserRegisterState(userRegisterState.value.copy(nameErrorMessage = "Informe o seu nome."))
            false
        } else if(name.length < 3) {
            updateUserRegisterState(userRegisterState.value.copy(nameErrorMessage = "O nome deve ter mais de 3 caracteres."))
            false
        }
        else {
            true
        }
    }

    private fun validateEmail(email: String): Boolean {
        val newState = _userRegisterState.value.copy(
            emailErrorMessage = null
        )
        updateUserRegisterState(newState)
        return if (email.isEmpty()) {
            updateUserRegisterState(userRegisterState.value.copy(emailErrorMessage = "Informe um e-mail."))
            false
        } else if(email.length > 50) {
            updateUserRegisterState(userRegisterState.value.copy(emailErrorMessage = "Tamanho máximo de 50 caracteres."))
            false
        } else {
            true
        }
    }

    private fun validatePassword(password: String, confirmPassword: String): Boolean {
        val newState = _userRegisterState.value.copy(
            passwordErrorMessage = null,
            confirmPasswordErrorMessage = null
        )
        updateUserRegisterState(newState)

        return if(password.isEmpty()) {
            updateUserRegisterState(newState.copy(passwordErrorMessage = "Informe uma senha."))
            false
        } else if(password.length > 50) {
            updateUserRegisterState(newState.copy(passwordErrorMessage = "Tamanho máximo de 50 caracteres."))
            false
        } else if (confirmPassword.isEmpty()) {
            updateUserRegisterState(newState.copy(confirmPasswordErrorMessage = "Repita a sua senha."))
            false
        } else if (password != confirmPassword) {
            updateUserRegisterState(
                newState.copy(
                    passwordErrorMessage = "As senhas estão diferentes.",
                    confirmPasswordErrorMessage = "As senhas estão diferentes."
                )
            )
            false
        } else {
            true
        }
    }

    private fun registerUserFirebase(name: String, email: String, password: String) {
        _userRegisterState.value = _userRegisterState.value.copy(isLoading = true)

        viewModelScope.launch {
            userRepository.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.i(TAG, "Usuário CRIADO com SUCESSO!")
                    val userId = it.user?.uid
                    if(userId != null) {
                        val newUser = User(
                            id = userId,
                            name = name,
                            email = email,
                            password = password,
                            orderWithCustomer = emptyList()
                        )
                        viewModelScope.launch {
                            saveUserFirestore(newUser)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.i(TAG, "ERRO ao CRIAR USUÁRIO.")
                    var emailErrorMessage = ""
                    var passwordErrorMessage = ""
                    var confirmPasswordErrorMessage = ""

                    try {
                        throw it
                    } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                        weakPassword.printStackTrace()
                        passwordErrorMessage = "Senha fraca, tente outra."
                        confirmPasswordErrorMessage = "Senha fraca, tente outra."
                    } catch (userAlreadyExist: FirebaseAuthUserCollisionException) {
                        userAlreadyExist.printStackTrace()
                        emailErrorMessage = "E-mail já cadastrado."
                    } catch (invalidCredentials: FirebaseAuthInvalidCredentialsException) {
                        emailErrorMessage = "E-mail inválido, tente novamente."
                    } catch (e: Exception) {
                        confirmPasswordErrorMessage = "Erro inesperado. Tente novamente mais tarde."
                    }

                    updateUserRegisterState(
                        userRegisterState.value.copy(
                            emailErrorMessage = emailErrorMessage.ifEmpty { null },
                            passwordErrorMessage = passwordErrorMessage.ifEmpty { null },
                            confirmPasswordErrorMessage = confirmPasswordErrorMessage.ifEmpty { null },
                            isLoading = false
                        )
                    )
                }
        }
    }

    private suspend fun saveUserFirestore(newUser: User) {
        userRepository.saveUserFirestore(newUser)
            .addOnSuccessListener {
                Log.i(TAG, "Usuário SALVO no FIRESTORE com SUCESSO!")
                _userRegisterState.value = _userRegisterState.value.copy(isRegistered = true)
            }
            .addOnFailureListener {
                Log.i(TAG, "ERRO ao SALVAR USUÁRIO no FIRESTORE.")
                try {
                    throw it
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }
}