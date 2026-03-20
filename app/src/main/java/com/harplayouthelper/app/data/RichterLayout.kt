package com.harplayouthelper.app.data

import kotlin.math.abs

data class HarmonicaGridData(
    val overblow: List<Note?>,
    val draw: List<Note>,
    val drawBend1: List<Note?>,
    val drawBend2: List<Note?>,
    val drawBend3: List<Note?>,
    val blow: List<Note>,
    val blowBend1: List<Note?>,
    val blowBend2: List<Note?>,
    val overdraw: List<Note?>,
)

object RichterLayout {
    // Semitone offsets from C4 (MIDI 60) for a C harmonica
    private val BLOW_OFFSETS = intArrayOf(0, 4, 7, 12, 16, 19, 24, 28, 31, 36)
    private val DRAW_OFFSETS = intArrayOf(2, 7, 11, 14, 17, 21, 23, 26, 29, 33)

    // Degree labels for all 12 chromatic intervals from root
    private val DEGREE_LABELS = arrayOf("1", "b2", "2", "b3", "3", "4", "b5", "5", "b6", "6", "b7", "7")

    fun computeGrid(key: HarmonicaKey): HarmonicaGridData {
        val baseMidi = 60 + key.semitonesFromC

        val blow = BLOW_OFFSETS.map { Note(baseMidi + it) }
        val draw = DRAW_OFFSETS.map { Note(baseMidi + it) }

        val overblow = Array<Note?>(10) { null }
        val drawBend1 = Array<Note?>(10) { null }
        val drawBend2 = Array<Note?>(10) { null }
        val drawBend3 = Array<Note?>(10) { null }
        val blowBend1 = Array<Note?>(10) { null }
        val blowBend2 = Array<Note?>(10) { null }
        val overdraw = Array<Note?>(10) { null }

        for (i in 0 until 10) {
            val blowMidi = blow[i].midi
            val drawMidi = draw[i].midi
            val interval = abs(drawMidi - blowMidi)

            if (drawMidi > blowMidi) {
                // Holes 1-6: draw bends and overblows
                val bendCount = interval - 1
                if (bendCount >= 1) drawBend1[i] = Note(drawMidi - 1)
                if (bendCount >= 2) drawBend2[i] = Note(drawMidi - 2)
                if (bendCount >= 3) drawBend3[i] = Note(drawMidi - 3)
                overblow[i] = Note(drawMidi + 1)
            } else if (blowMidi > drawMidi) {
                // Holes 7-10: blow bends and overdraws
                val bendCount = interval - 1
                if (bendCount >= 1) blowBend1[i] = Note(blowMidi - 1)
                if (bendCount >= 2) blowBend2[i] = Note(blowMidi - 2)
                overdraw[i] = Note(blowMidi + 1)
            }
        }

        return HarmonicaGridData(
            overblow = overblow.toList(),
            draw = draw,
            drawBend1 = drawBend1.toList(),
            drawBend2 = drawBend2.toList(),
            drawBend3 = drawBend3.toList(),
            blow = blow,
            blowBend1 = blowBend1.toList(),
            blowBend2 = blowBend2.toList(),
            overdraw = overdraw.toList(),
        )
    }

    fun isNoteInHighlight(note: Note, rootPitchClass: Int, intervals: Set<Int>): Boolean {
        return ((note.pitchClass - rootPitchClass + 12) % 12) in intervals
    }

    fun playingKeyRoot(key: HarmonicaKey, position: Position): Int {
        return (key.semitonesFromC + position.semitoneOffset) % 12
    }

    fun playingKeyName(key: HarmonicaKey, position: Position): String {
        val root = playingKeyRoot(key, position)
        return Note.NOTE_NAMES[root]
    }

    fun chordRootPitchClass(playingKeyRoot: Int, degree: ChordDegree): Int {
        return (playingKeyRoot + degree.semitonesFromRoot) % 12
    }

    fun chordPitchClasses(playingKeyRoot: Int, degree: ChordDegree, quality: ChordQuality): Set<Int> {
        val chordRoot = chordRootPitchClass(playingKeyRoot, degree)
        return quality.intervals.map { (chordRoot + it) % 12 }.toSet()
    }

    fun degreeLabel(note: Note, rootPitchClass: Int): String {
        val interval = (note.pitchClass - rootPitchClass + 12) % 12
        return DEGREE_LABELS[interval]
    }
}
