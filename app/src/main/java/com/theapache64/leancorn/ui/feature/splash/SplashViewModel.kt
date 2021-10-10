package com.theapache64.leancorn.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.leancorn.util.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _shouldGoToHome = mutableEventFlow<Boolean>()
    val shouldGoToHome = _shouldGoToHome.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(1500L)
            _shouldGoToHome.tryEmit(true)
        }
    }
}