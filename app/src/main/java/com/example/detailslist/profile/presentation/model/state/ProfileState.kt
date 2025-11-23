package com.example.detailslist.profile.presentation.model.state

import android.net.Uri

interface ProfileState {
    val name: String
    val photoUri: Uri
}