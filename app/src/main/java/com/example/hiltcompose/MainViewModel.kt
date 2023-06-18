package com.example.hiltcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltcompose.domain.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MainState(
    val title: String,
    val savedText: String
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storage: Storage
) : ViewModel() {

    private val initialState = MainState(
        title = "Compose+Hilt Sample",
        savedText = storage.getSavedText()
    )

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<MainState> = _state

    fun setSavedText(text: String) = viewModelScope.launch {
        storage.setSavedText(text)

        _state.update {
            it.copy(
                savedText = storage.getSavedText()
            )
        }
    }
}