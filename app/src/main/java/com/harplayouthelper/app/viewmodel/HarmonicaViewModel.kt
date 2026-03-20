package com.harplayouthelper.app.viewmodel

import androidx.lifecycle.ViewModel
import com.harplayouthelper.app.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class DisplayMode { NOTES, DEGREES }

enum class NoteHighlight { NONE, SCALE, CHORD, BOTH }

data class HarmonicaUiState(
    val key: HarmonicaKey = HarmonicaKey.C,
    val position: Position = Position.FIRST,
    val scale: Scale? = null,
    val chordDegree: ChordDegree? = null,
    val chordQuality: ChordQuality? = null,
    val displayMode: DisplayMode = DisplayMode.NOTES,
    val gridData: HarmonicaGridData = RichterLayout.computeGrid(HarmonicaKey.C),
    val playingKeyRoot: Int = 0,
    val playingKeyName: String = "C",
    val scalePitchClasses: Set<Int>? = null,
    val chordPitchClasses: Set<Int>? = null,
)

class HarmonicaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HarmonicaUiState())
    val uiState: StateFlow<HarmonicaUiState> = _uiState.asStateFlow()

    fun setKey(key: HarmonicaKey) {
        updateState(_uiState.value.copy(key = key))
    }

    fun setPosition(position: Position) {
        updateState(_uiState.value.copy(position = position))
    }

    fun setScale(scale: Scale?) {
        updateState(_uiState.value.copy(scale = scale))
    }

    fun setChordDegree(degree: ChordDegree?) {
        if (degree == null) {
            updateState(_uiState.value.copy(chordDegree = null, chordQuality = null))
        } else {
            val quality = _uiState.value.chordQuality ?: ChordQuality.MAJOR
            updateState(_uiState.value.copy(chordDegree = degree, chordQuality = quality))
        }
    }

    fun setChordQuality(quality: ChordQuality?) {
        updateState(_uiState.value.copy(chordQuality = quality))
    }

    fun setDisplayMode(mode: DisplayMode) {
        _uiState.value = _uiState.value.copy(displayMode = mode)
    }

    private fun updateState(state: HarmonicaUiState) {
        val gridData = RichterLayout.computeGrid(state.key)
        val root = RichterLayout.playingKeyRoot(state.key, state.position)
        val keyName = RichterLayout.playingKeyName(state.key, state.position)

        val scalePitchClasses = state.scale?.intervals?.map { (root + it) % 12 }?.toSet()

        val chordPitchClasses = if (state.chordDegree != null && state.chordQuality != null) {
            RichterLayout.chordPitchClasses(root, state.chordDegree, state.chordQuality)
        } else null

        _uiState.value = state.copy(
            gridData = gridData,
            playingKeyRoot = root,
            playingKeyName = keyName,
            scalePitchClasses = scalePitchClasses,
            chordPitchClasses = chordPitchClasses,
        )
    }

    fun getNoteHighlight(note: Note?): NoteHighlight {
        if (note == null) return NoteHighlight.NONE
        val state = _uiState.value
        val inScale = state.scalePitchClasses?.contains(note.pitchClass) == true
        val inChord = state.chordPitchClasses?.contains(note.pitchClass) == true
        return when {
            inScale && inChord -> NoteHighlight.BOTH
            inChord -> NoteHighlight.CHORD
            inScale -> NoteHighlight.SCALE
            else -> NoteHighlight.NONE
        }
    }

    fun getDegreeLabel(note: Note): String {
        return RichterLayout.degreeLabel(note, _uiState.value.playingKeyRoot)
    }
}
