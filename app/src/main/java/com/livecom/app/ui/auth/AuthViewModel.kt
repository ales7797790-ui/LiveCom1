package com.livecom.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livecom.app.core.util.Result
import com.livecom.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Result<String>>(Result.Loading)
    val state: StateFlow<Result<String>> = _state

    init { signIn() }

    fun signIn() = viewModelScope.launch {
        _state.value = Result.Loading
        _state.value = repo.ensureSignedIn()
    }
}
