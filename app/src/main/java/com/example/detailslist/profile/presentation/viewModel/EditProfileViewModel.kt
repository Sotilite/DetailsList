package com.example.detailslist.profile.presentation.viewModel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailslist.profile.data.repository.ProfileRepository
import com.example.detailslist.profile.presentation.model.state.EditProfileState
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val repository: ProfileRepository,
): ViewModel() {
    private val mutableNavigationEvent = MutableSharedFlow<String>()
    val navigationEvent = mutableNavigationEvent.asSharedFlow()

    private val mutableState = MutableEditProfileState()
    val viewState = mutableState as EditProfileState

    init {
        viewModelScope.launch {
            repository.getProfile()?.let {
                mutableState.name = it.name
                mutableState.url = it.url
                mutableState.photoUri = Uri.parse(it.photoUri)
            }
        }
    }

    fun onNameChanged(name: String) {
        mutableState.name = name
    }

    fun onUrlChanged(url: String) {
        mutableState.url = url
    }

    fun onBackClicked() {
        viewModelScope.launch {
            mutableNavigationEvent.emit("back")
        }
    }

    fun onImageSelected(uri: Uri?) {
        uri?.let {
            mutableState.photoUri = it
        }
    }

    fun onPermissionClosed() {
        mutableState.isNeedToShowPermission = false
    }

    fun onAvatarClicked() {
        mutableState.isNeedToShowSelect = true
    }

    fun onSelectDismiss() {
        mutableState.isNeedToShowSelect = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDoneClicked() {
        viewModelScope.launch {
            repository.setProfile(
                mutableState.photoUri.toString(),
                viewState.name,
                viewState.url
            )
            onBackClicked()
        }
    }

    private class MutableEditProfileState : EditProfileState {
        override var photoUri: Uri by mutableStateOf(Uri.EMPTY)
        override var name by mutableStateOf("")
        override var url by mutableStateOf("")
        override var isNeedToShowPermission by mutableStateOf(false)
        override var isNeedToShowSelect by mutableStateOf(false)
    }
}