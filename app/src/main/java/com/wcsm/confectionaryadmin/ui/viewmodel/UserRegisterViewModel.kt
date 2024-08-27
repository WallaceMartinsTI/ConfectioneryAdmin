package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.wcsm.confectionaryadmin.data.model.entities.FirestoreUser
import com.wcsm.confectionaryadmin.data.model.states.UserRegisterState
import com.wcsm.confectionaryadmin.data.repository.UserRepository
import com.wcsm.confectionaryadmin.ui.util.Constants.AUTH_TAG
import com.wcsm.confectionaryadmin.ui.util.Constants.FIRESTORE_TAG
import com.wcsm.confectionaryadmin.ui.util.Constants.ROOM_TAG
import com.wcsm.confectionaryadmin.ui.util.formatNameCapitalizeFirstChar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
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

        try {
            val name = formatNameCapitalizeFirstChar(userRegisterState.value.name.trim())
            val email = userRegisterState.value.email.trim().lowercase()
            val password = userRegisterState.value.password.trim()
            val confirmPassword = userRegisterState.value.confirmPassword.trim()

            if(isAllFieldsValid(name, email, password, confirmPassword)) {
                Log.i(AUTH_TAG, "New user object created!")
                registerUserFirebase(name, email, password)
            } else {
                updateUserRegisterState(
                    userRegisterState.value.copy(
                        isLoading = false
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(ROOM_TAG, "Error creating a new user object.", e)
            updateUserRegisterState(
                userRegisterState.value.copy(
                    isLoading = false
                )
            )
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
            delay(2000)
            userRepository.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.i(AUTH_TAG, "New user created successfully!")
                    val userId = it.user?.uid
                    if(userId != null) {
                        val newFirestoreUser = FirestoreUser(
                            id = userId,
                            name = name,
                            email = email
                        )
                        viewModelScope.launch {
                            saveUserFirestore(newFirestoreUser)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.i(AUTH_TAG, "Error creating a new user.")
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
                    updateUserRegisterState(
                        userRegisterState.value.copy(
                            isLoading = false
                        )
                    )
                }
        }
    }

    private suspend fun saveUserFirestore(newFirestoreUser: FirestoreUser) {
        userRepository.saveUserFirestore(newFirestoreUser)
            .addOnSuccessListener {
                Log.i(FIRESTORE_TAG, "New user saved in firestore successfully!")
                _userRegisterState.value = _userRegisterState.value.copy(isRegistered = true)
                updateUserRegisterState(
                    userRegisterState.value.copy(
                        isLoading = false
                    )
                )
            }
            .addOnFailureListener {
                Log.i(FIRESTORE_TAG, "Error saving new user in firestore.")
                try {
                    throw it
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                updateUserRegisterState(
                    userRegisterState.value.copy(
                        isLoading = false
                    )
                )
            }
    }
}