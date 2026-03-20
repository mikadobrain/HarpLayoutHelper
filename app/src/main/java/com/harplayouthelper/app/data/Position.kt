package com.harplayouthelper.app.data

enum class Position(val label: String, val semitoneOffset: Int) {
    FIRST("1st Position", 0),
    SECOND("2nd Position", 7),
    THIRD("3rd Position", 2),
    FOURTH("4th Position", 9),
    FIFTH("5th Position", 4);

    companion object {
        val ALL = entries.toList()
    }
}
