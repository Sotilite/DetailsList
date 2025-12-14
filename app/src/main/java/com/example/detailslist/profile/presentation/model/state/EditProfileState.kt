package com.example.detailslist.profile.presentation.model.state

import android.net.Uri

interface EditProfileState {
    val photoUri: Uri
    val name: String
    val url: String
    val favoriteLessonTime: String
    val timeErrorMessage: String
    val isNeedToShowPermission: Boolean
    val isNeedToShowSelect: Boolean
}