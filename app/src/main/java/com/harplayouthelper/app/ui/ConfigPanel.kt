package com.harplayouthelper.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harplayouthelper.app.data.*
import com.harplayouthelper.app.viewmodel.DisplayMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigPanel(
    selectedKey: HarmonicaKey,
    selectedPosition: Position,
    selectedScale: Scale?,
    selectedChordDegree: ChordDegree?,
    selectedChordQuality: ChordQuality?,
    displayMode: DisplayMode,
    onKeyChange: (HarmonicaKey) -> Unit,
    onPositionChange: (Position) -> Unit,
    onScaleChange: (Scale?) -> Unit,
    onChordDegreeChange: (ChordDegree?) -> Unit,
    onChordQualityChange: (ChordQuality?) -> Unit,
    onDisplayModeChange: (DisplayMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        // Row 1: Harp-Key + Position
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            DropdownSelector(
                label = "Harp-Key",
                selected = selectedKey.displayName,
                options = HarmonicaKey.ALL.map { it.displayName },
                onSelect = { index -> onKeyChange(HarmonicaKey.ALL[index]) },
                modifier = Modifier.weight(1f),
            )
            DropdownSelector(
                label = "Position",
                selected = selectedPosition.label,
                options = Position.ALL.map { it.label },
                onSelect = { index -> onPositionChange(Position.ALL[index]) },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Row 2: Scale
        DropdownSelector(
            label = "Scale",
            selected = selectedScale?.displayName ?: "None",
            options = listOf("None") + Scale.ALL.map { it.displayName },
            onSelect = { index ->
                onScaleChange(if (index == 0) null else Scale.ALL[index - 1])
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Row 3: Chord Degree + Quality
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            // Generate chord degree options with root notes
            val playingKeyRoot = RichterLayout.playingKeyRoot(selectedKey, selectedPosition)
            val chordDegreeOptions = listOf("None") + ChordDegree.ALL.map { degree ->
                val chordRoot = RichterLayout.chordRootPitchClass(playingKeyRoot, degree)
                val rootNoteName = Note.NOTE_NAMES[chordRoot]
                "${degree.label} ($rootNoteName)"
            }
            val selectedChordDisplay = if (selectedChordDegree == null) "None" else {
                val chordRoot = RichterLayout.chordRootPitchClass(playingKeyRoot, selectedChordDegree)
                val rootNoteName = Note.NOTE_NAMES[chordRoot]
                "${selectedChordDegree.label} ($rootNoteName)"
            }

            DropdownSelector(
                label = "Chord Degree",
                selected = selectedChordDisplay,
                options = chordDegreeOptions,
                onSelect = { index ->
                    onChordDegreeChange(if (index == 0) null else ChordDegree.ALL[index - 1])
                },
                modifier = Modifier.weight(1f),
            )
            DropdownSelector(
                label = "Chord Type",
                selected = selectedChordQuality?.displayName ?: "None",
                options = listOf("None") + ChordQuality.ALL.map { it.displayName },
                onSelect = { index ->
                    onChordQualityChange(if (index == 0) null else ChordQuality.ALL[index - 1])
                },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Row 4: Display mode toggle (Notes vs Degrees)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Notes",
                fontSize = 14.sp,
                color = if (displayMode == DisplayMode.NOTES)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
            Switch(
                checked = displayMode == DisplayMode.DEGREES,
                onCheckedChange = { checked ->
                    onDisplayModeChange(if (checked) DisplayMode.DEGREES else DisplayMode.NOTES)
                },
            )
            Text(
                text = "Degrees",
                fontSize = 14.sp,
                color = if (displayMode == DisplayMode.DEGREES)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownSelector(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            singleLine = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(index)
                        expanded = false
                    },
                )
            }
        }
    }
}
