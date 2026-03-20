package com.harplayouthelper.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.harplayouthelper.app.data.Note
import com.harplayouthelper.app.data.RichterLayout
import com.harplayouthelper.app.viewmodel.DisplayMode
import com.harplayouthelper.app.viewmodel.HarmonicaViewModel

@Composable
fun HarmonicaScreen(
    viewModel: HarmonicaViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp),
        ) {
            Text(
                text = "${state.key.displayName}-Diatonic",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            // Show playing key info
            val infoText = buildString {
                append("Playing Key: ${state.playingKeyName}")
                if (state.scale != null) {
                    append(" ${state.scale!!.displayName}")
                }
                if (state.chordDegree != null && state.chordQuality != null) {
                    val chordRoot = RichterLayout.chordRootPitchClass(
                        state.playingKeyRoot, state.chordDegree!!
                    )
                    val chordName = Note.NOTE_NAMES[chordRoot]
                    append(" | Chord: $chordName ${state.chordQuality!!.displayName}")
                }
            }
            if (state.scale != null || state.chordDegree != null) {
                Text(
                    text = infoText,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ConfigPanel(
                selectedKey = state.key,
                selectedPosition = state.position,
                selectedScale = state.scale,
                selectedChordDegree = state.chordDegree,
                selectedChordQuality = state.chordQuality,
                displayMode = state.displayMode,
                onKeyChange = viewModel::setKey,
                onPositionChange = viewModel::setPosition,
                onScaleChange = viewModel::setScale,
                onChordDegreeChange = viewModel::setChordDegree,
                onChordQualityChange = viewModel::setChordQuality,
                onDisplayModeChange = viewModel::setDisplayMode,
            )

            Spacer(modifier = Modifier.height(16.dp))

            val hasAnyHighlight = state.scalePitchClasses != null || state.chordPitchClasses != null

            HarmonicaGrid(
                gridData = state.gridData,
                getHighlight = { note -> viewModel.getNoteHighlight(note) },
                getDisplayText = { note ->
                    if (note == null) null
                    else when (state.displayMode) {
                        DisplayMode.NOTES -> note.name
                        DisplayMode.DEGREES -> viewModel.getDegreeLabel(note)
                    }
                },
                hasAnyHighlightActive = hasAnyHighlight,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
