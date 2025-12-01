package com.example.profile.presentation.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profile.data.repository.ProfileRepository
import com.example.profile.presentation.model.state.ProfileState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
): ViewModel() {
    private val mutableNavigationEvent = MutableSharedFlow<String>()
    val navigationEvent = mutableNavigationEvent.asSharedFlow()

    fun onEditProfileClicked() {
        viewModelScope.launch {
            mutableNavigationEvent.emit("edit_profile")
        }
    }

    private val mutableState = MutableProfileState()
    val viewState = mutableState as ProfileState

    init {
        viewModelScope.launch {
            repository.observeProfile().collect {
                mutableState.name = it.name
                mutableState.photoUri = Uri.parse(it.photoUri)
            }
        }
    }

    private class MutableProfileState: ProfileState {
        override var name by mutableStateOf("")
        override var photoUri by mutableStateOf(Uri.EMPTY)
    }
}