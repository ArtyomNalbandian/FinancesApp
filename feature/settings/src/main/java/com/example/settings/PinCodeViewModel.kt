package com.example.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.util.PinCodeStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class PinCodeMode {
    object Set : PinCodeMode()
    object Confirm : PinCodeMode()
    object Check : PinCodeMode()
    object Change : PinCodeMode()
}

class PinCodeViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _mode = MutableStateFlow<PinCodeMode>(PinCodeMode.Check)
    val mode: StateFlow<PinCodeMode> = _mode.asStateFlow()
    
    private var isSettingsMode = false

    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin.asStateFlow()

    private val _confirmPin = MutableStateFlow("")
    val confirmPin: StateFlow<String> = _confirmPin.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isPinVerified = MutableStateFlow(false)
    val isPinVerified: StateFlow<Boolean> = _isPinVerified.asStateFlow()

    private var firstPin: String? = null
    private var oldPin: String? = null
    private var changeStep: Int = 0

    fun setMode(newMode: PinCodeMode) {
        _mode.value = newMode
        _pin.value = ""
        _confirmPin.value = ""
        _error.value = null
        _isPinVerified.value = false
        firstPin = null
        oldPin = null
        changeStep = 0
    }
    
    fun setSettingsMode(isSettings: Boolean) {
        isSettingsMode = isSettings
    }

    fun onDigit(digit: Char, onSubmit: () -> Unit) {
        // Очищаем ошибку при новом вводе
        if (_error.value != null) {
            _error.value = null
        }
        
        when (_mode.value) {
            is PinCodeMode.Set, is PinCodeMode.Check -> if (_pin.value.length < 4) {
                _pin.value += digit
                if (_pin.value.length == 4) onSubmit()
            }

            is PinCodeMode.Confirm -> if (_confirmPin.value.length < 4) {
                _confirmPin.value += digit
                if (_confirmPin.value.length == 4) onSubmit()
            }

            is PinCodeMode.Change -> {
                when (changeStep) {
                    0, 1 -> if (_pin.value.length < 4) {
                        _pin.value += digit
                        if (_pin.value.length == 4) onSubmit()
                    }

                    2 -> if (_confirmPin.value.length < 4) {
                        _confirmPin.value += digit
                        if (_confirmPin.value.length == 4) onSubmit()
                    }
                }
            }
        }
    }

    fun onDelete() {
        when (_mode.value) {
            is PinCodeMode.Set, is PinCodeMode.Check -> if (_pin.value.isNotEmpty()) _pin.value =
                _pin.value.dropLast(1)

            is PinCodeMode.Confirm -> if (_confirmPin.value.isNotEmpty()) _confirmPin.value =
                _confirmPin.value.dropLast(1)

            is PinCodeMode.Change -> when (changeStep) {
                0, 1 -> if (_pin.value.isNotEmpty()) _pin.value = _pin.value.dropLast(1)
                2 -> if (_confirmPin.value.isNotEmpty()) _confirmPin.value =
                    _confirmPin.value.dropLast(1)
            }
        }
    }

    fun reset() {
        _pin.value = ""
        _confirmPin.value = ""
        _error.value = null
        _isPinVerified.value = false
        firstPin = null
        oldPin = null
        changeStep = 0
    }

    fun onSubmit(onSuccess: () -> Unit) {
        when (_mode.value) {
            is PinCodeMode.Set -> {
                if (_pin.value.length == 4) {
                    firstPin = _pin.value
                    _confirmPin.value = ""
                    _mode.value = PinCodeMode.Confirm
                }
            }

            is PinCodeMode.Confirm -> {
                if (_confirmPin.value.length == 4) {
                    if (_confirmPin.value == firstPin) {
                        viewModelScope.launch {
                            PinCodeStorage.savePin(context, _confirmPin.value)
                            reset()
                            _mode.value = PinCodeMode.Check
                            onSuccess()
                        }
                    } else {
                        _error.value = "Пин-коды не совпадают"
                        _confirmPin.value = ""
                    }
                }
            }

            is PinCodeMode.Check -> {
                if (_pin.value.length == 4) {
                    viewModelScope.launch {
                        val saved = PinCodeStorage.getPin(context)
                        if (_pin.value == saved) {
                            // Если это настройки, показываем кнопки изменения
                            // Если это вход в приложение, сразу переходим
                            if (isSettingsMode) {
                                _isPinVerified.value = true
                            } else {
                                reset()
                                onSuccess()
                            }
                        } else {
                            _error.value = "Неверный пин-код (введено: ${_pin.value}, сохранено: $saved)"
                            _pin.value = "" // Очищаем поле при ошибке
                        }
                    }
                }
            }

            is PinCodeMode.Change -> {
                when (changeStep) {
                    0 -> { // ввод старого
                        viewModelScope.launch {
                            val saved = PinCodeStorage.getPin(context)
                            if (_pin.value == saved) {
                                oldPin = _pin.value
                                _pin.value = ""
                                changeStep = 1
                            } else {
                                _error.value = "Неверный старый пин-код"
                                _pin.value = ""
                            }
                        }
                    }

                    1 -> { // ввод нового
                        if (_pin.value.length == 4) {
                            firstPin = _pin.value
                            _confirmPin.value = ""
                            changeStep = 2
                        }
                    }

                    2 -> { // подтверждение нового
                        if (_confirmPin.value.length == 4) {
                            if (_confirmPin.value == firstPin) {
                                viewModelScope.launch {
                                    PinCodeStorage.savePin(context, _confirmPin.value)
                                    reset()
                                    _mode.value = PinCodeMode.Check
                                    onSuccess()
                                }
                            } else {
                                _error.value = "Пин-коды не совпадают"
                                _confirmPin.value = ""
                            }
                        }
                    }
                }
            }
        }
    }

    fun isPinSet(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            callback(PinCodeStorage.isPinSet(context))
        }
    }

    fun clearPin(onCleared: () -> Unit) {
        viewModelScope.launch {
            PinCodeStorage.clearPin(context)
            onCleared()
        }
    }

    fun getChangeStep(): Int = changeStep
}
