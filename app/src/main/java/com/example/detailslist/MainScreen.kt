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
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.example.core.navigation.EntryProviderInstaller
import com.example.detailslist.characters.presentation.model.CharacterUiModel
import com.example.detailslist.characters.presentation.view.CharacterDetailsView
import com.example.detailslist.characters.presentation.view.CharactersListView
import com.example.detailslist.characters.presentation.view.CharactersSettingsDialog
import com.example.core.navigation.Route
import com.example.core.navigation.TopLevelBackStack
import com.example.core.navigation.TopLevelRoute
import com.example.profile.di.PROFILE_QUALIFIER
import com.example.profile.di.Profile
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

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

    val profileEntryProvider by inject<EntryProviderInstaller>(
        clazz = EntryProviderInstaller::class.java,
        qualifier = named(PROFILE_QUALIFIER)
    )

    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Episodes, Characters, Profile).forEach { route ->
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
                profileEntryProvider.let { builder -> this.builder() }
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
