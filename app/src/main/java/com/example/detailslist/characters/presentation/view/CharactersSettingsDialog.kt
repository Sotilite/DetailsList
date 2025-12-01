package com.example.detailslist.characters.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.detailslist.characters.presentation.model.CharactersSettingsState
import com.example.detailslist.characters.presentation.viewModel.CharactersSettingsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.window.Dialog
import com.example.uikit.component.Spacing

@Composable
fun CharactersSettingsDialog() {
    val viewModel = koinViewModel<CharactersSettingsViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    CharacterSettingsDialogContent(
        state,
        viewModel::onAliveFirstCheckedChange,
        viewModel::onBack,
        viewModel::onSaveClicked,
    )
}

@Composable
private fun CharacterSettingsDialogContent(
    state: CharactersSettingsState,
    onAliveFirstCheckedChange: (Boolean) -> Unit = {},
    onBack: () -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onBack() }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(Spacing.medium),
        ) {
            Text(
                text = "Настройки",
                style = MaterialTheme.typography.titleMedium,
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = state.aliveFirst,
                    onCheckedChange = { onAliveFirstCheckedChange(it) }
                )
                Spacer(Modifier.width(Spacing.medium))
                Text("Сначала живые персонажи")
            }

            TextButton(
                onClick = onSaveClick,
                modifier = Modifier
                    .align(Alignment.End),
            ) {
                Text("Сохранить")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterSettingsDialogPreview() {
    CharacterSettingsDialogContent(CharactersSettingsState(true))
}