package com.perqin.andori

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val isEnabled = FloatingService.isEnabled()
}
