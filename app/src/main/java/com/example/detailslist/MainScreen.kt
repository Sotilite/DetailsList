package com.example.detailslist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.detailslist.characters.presentation.view.CharactersListScreen
import com.example.detailslist.navigation.Route
import com.example.detailslist.navigation.TopLevelBackStack

interface TopLevelRoute : Route {
    val icon: ImageVector
}
data object Characters: TopLevelRoute {
    override val icon = Icons.Default.Face
}
data object Episodes: TopLevelRoute {
    override val icon = Icons.Default.Person
}

@Composable
fun MainScreen() {
    val topLevelBackStack = remember { TopLevelBackStack<Route>(Characters) }
    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Characters, Episodes).forEach { route ->
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
            entryProvider = entryProvider {
                entry<Characters> {
                    CharactersListScreen()
                }
            }
        )
    }
}
