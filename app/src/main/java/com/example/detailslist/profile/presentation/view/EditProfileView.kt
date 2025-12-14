package com.example.detailslist.profile.presentation.view

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.detailslist.R
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import com.example.detailslist.profile.presentation.utils.timePickerView
import com.example.detailslist.profile.presentation.viewModel.EditProfileViewModel
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.util.Date

@Parcelize
class EditProfileView(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content(modifier: Modifier) {
        val context = LocalContext.current
        val viewModel = koinViewModel<EditProfileViewModel>()
        val viewState = viewModel.viewState

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collect { destination ->
                when (destination) {
                    "back" -> {
                        val topLevelBackStack by inject <TopLevelBackStack<Route>>(clazz = TopLevelBackStack::class.java)
                        topLevelBackStack.removeLast()
                    }
                }
            }
        }

        val pickMedia: ActivityResultLauncher<PickVisualMediaRequest> =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                viewModel.onImageSelected(uri)
            }

        val cameraPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if(isGranted) {
                val dialog = AlertDialog.Builder(context)
                    .setMessage("Дайте разрешение для возможности сделать фотографию...")
                    .setCancelable(false)
                    .setPositiveButton("OK") { _, _ -> }
                dialog.show()
            }
            viewModel.onPermissionClosed()
        }

        var imageUri by remember { mutableStateOf<Uri?>(null)}
        val mGetContent = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success: Boolean ->
            if(success) {
                viewModel.onImageSelected(imageUri)
            }
        }

        var hasNotificationPermission by remember { mutableStateOf(false) }
        val notificationPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            hasNotificationPermission = isGranted
        }
        LaunchedEffect(Unit) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                hasNotificationPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            }
            if(!hasNotificationPermission) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.edit_profile))
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    viewModel.onBackClicked()
                                }
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable(
                                    enabled = viewState.timeErrorMessage.isEmpty(),
                                    onClick = {
                                        viewModel.onDoneClicked()
                                    }
                                )
                        )
                    },
                    modifier = Modifier.shadow(elevation = 1.dp)
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GlideImage(
                    model = viewState.photoUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .clickable {
                            viewModel.onAvatarClicked()
                        }
                ) { request ->
                    request
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                }
                TextField(
                    value = viewState.name,
                    onValueChange = {
                        viewModel.onNameChanged(it)
                    },
                    label = { Text(text = stringResource(R.string.name)) }
                )
                TextField(
                    value = viewState.url,
                    onValueChange = {
                        viewModel.onUrlChanged(it)
                    },
                    label = { Text(text = stringResource(R.string.link)) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f),
                        value = viewState.favoriteLessonTime,
                        onValueChange = viewModel::onFavoriteLessonTimeChange,
                        label = { Text("Время любимой пары") },
                        placeholder = { Text("HH:mm") },
                        isError = viewState.timeErrorMessage != "",
                    )

                    IconButton(onClick = {
                        timePickerView(
                            context = context,
                            currentTime = viewState.favoriteLessonTime,
                            onTimeSelected = { hour, minute ->
                                viewModel.onTimeSelected(hour, minute)
                            }
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Выбрать время"
                        )
                    }
                }
                if(!viewState.timeErrorMessage.isEmpty()) {
                    Text(
                        text = viewState.timeErrorMessage,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                if (viewState.isNeedToShowPermission) {
                    LaunchedEffect(Unit) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            cameraPermissionLauncher.launch(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        }
                    }
                }

                fun onCameraSelected() {
                    val baseDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    )
                    val pictureFile = File(baseDir, "picture_${Date().time}.jpg")
                    imageUri = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".provider",
                        pictureFile
                    )
                    imageUri?.let {
                        mGetContent.launch(it)
                    }
                }

                if(viewState.isNeedToShowSelect) {
                    Dialog(
                        onDismissRequest = {
                            viewModel.onSelectDismiss()
                        }
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.camera),
                                    modifier = Modifier
                                        .clickable {
                                            onCameraSelected()
                                            viewModel.onSelectDismiss()
                                        }
                                )
                                Text(
                                    text = stringResource(R.string.gallery),
                                    modifier = Modifier
                                        .clickable {
                                            pickMedia
                                                .launch(PickVisualMediaRequest(
                                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                                ))
                                            viewModel.onSelectDismiss()
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}