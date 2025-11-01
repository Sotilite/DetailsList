package com.example.detailslist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.view.CharacterDetailsView
import com.example.detailslist.characters.presentation.view.CharactersListView
import com.example.detailslist.characters.presentation.view.CharactersSettingsDialog
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack
import org.koin.java.KoinJavaComponent.inject

interface TopLevelRoute : Route {
    val icon: ImageVector
}

data object Episodes: TopLevelRoute {
    override val icon = Icons.Default.Build
}
data object Characters: TopLevelRoute {
    override val icon = Icons.Default.Person
}

data class CharacterDetails(val character: CharacterUiModel) : Route

data object CharactersSettings : Route

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val topLevelBackStack by inject<TopLevelBackStack<Route>>(TopLevelBackStack::class.java)

    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Episodes, Characters).forEach { route ->
                NavigationBarItem(
                    icon = { Icon(route.icon, null) },
                    selected = topLevelBackStack.topLevelKey == route,
                    onClick = {
                        topLevelBackStack.addTopLevel(route)
                    }
                )
            }
        }
    }) { padding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            modifier = Modifier.padding(padding),
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Episodes> {
                    EpisodesListView("Episodes view")
                }
                entry<Characters> {
                    CharactersListView()
                }
                entry<CharacterDetails> {
                    CharacterDetailsView(it.character)
                }
                entry<CharactersSettings>(
                    metadata = DialogSceneStrategy.dialog(DialogProperties())
                ) {
                    CharactersSettingsDialog()
                }
            }
        )
    }
}

@Composable
fun EpisodesListView(text: String) {
    Text(
        text = text
    )
}
