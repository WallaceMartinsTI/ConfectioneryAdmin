package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private var _showChooseWhatWillCreateDialog = MutableStateFlow(false)
    val showChooseWhatWillCreateDialog = _showChooseWhatWillCreateDialog.asStateFlow()

    fun changeShowChooseWhatWillCreateDialog(status: Boolean) {
        _showChooseWhatWillCreateDialog.value = status
    }

}