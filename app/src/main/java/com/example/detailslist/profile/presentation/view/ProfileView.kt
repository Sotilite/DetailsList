package com.example.detailslist.profile.presentation.view

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.detailslist.EditProfile
import com.example.detailslist.R
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import com.example.detailslist.profile.presentation.BroadcastReceiver
import com.example.detailslist.profile.presentation.viewModel.ProfileViewModel
import com.example.detailslist.ui.theme.Typography
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import com.github.terrakok.modo.stack.forward
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.inject
import java.io.File

@Parcelize
class ProfileView(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
    @Composable
    override fun Content(modifier: Modifier) {
        val topLevelBackStack by inject<TopLevelBackStack<Route>>(clazz = TopLevelBackStack::class.java)
        val context = LocalContext.current
        val viewModel = koinViewModel<ProfileViewModel>()
        val viewState = viewModel.viewState

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collect { destination ->
                when (destination) {
                    "edit_profile" -> {
                        topLevelBackStack.add(EditProfile)
                    }
                }
            }
        }

        InitBroadcastReceiver(context)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.profile)) },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.Companion
                                .padding(end = 8.dp)
                                .clickable {
                                    viewModel.onEditProfileClicked()
                                }
                        )
                    }
                )
            },
            contentWindowInsets = WindowInsets(0.dp)
        ) { padding ->
            Column(
                modifier = Modifier.Companion
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                GlideImage(
                    model = viewState.photoUri,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(128.dp),
                    contentDescription = "photo",
                ){ request ->
                    request
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                }
                Text(
                    text = viewState.name,
                    style = Typography.headlineLarge
                )
                Button({ enqueueDownloadRequest(
                    "https://elibrary.ru/download/elibrary_44394445_69180276.pdf", context
                )}) {
                    Text(
                        text = stringResource(R.string.resume)
                    )
                }
            }
        }
    }

    @Composable
    private fun InitBroadcastReceiver(context: Context) {
        BroadcastReceiver(
            systemAction = DOWNLOAD_COMPLETE_ACTION,
            onSystemEvent = { intent ->
                if (intent?.action == DOWNLOAD_COMPLETE_ACTION) {
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                    if (id != -1L) {
                        navigateToDownloadedInvoice(context)
                    }
                }
            })
    }

    private fun enqueueDownloadRequest(
        url: String,
        context: Context
    ) {
        val request: DownloadManager.Request = DownloadManager.Request(url.toUri())
        with(request) {
            setTitle("Test pdf")
            setMimeType("pdf")
            setDescription("Downloading pdf...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "document.pdf"
            )
        }
        val manager: DownloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    private fun navigateToDownloadedInvoice(context: Context) {
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ),
                "Test.pdf"
            )
            val uri = FileProvider.getUriForFile(
                context,
                context.applicationContext?.packageName + ".provider",
                file
            )
            val intent =
                Intent(Intent.ACTION_VIEW)
            with(intent) {
                setDataAndType(
                    uri,
                    "application/pdf"
                )
                flags =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object{
        private const val DOWNLOAD_COMPLETE_ACTION = "android.intent.action.DOWNLOAD_COMPLETE"
    }
}