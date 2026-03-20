package com.harplayouthelper.app.data

@JvmInline
value class Note(val midi: Int) {
    val name: String get() = NOTE_NAMES[midi % 12]
    val octave: Int get() = (midi / 12) - 1
    val pitchClass: Int get() = midi % 12

    companion object {
        val NOTE_NAMES = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")
    }
}
