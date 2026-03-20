package com.harplayouthelper.app.data

enum class ChordQuality(val displayName: String, val intervals: Set<Int>) {
    MAJOR("Major", setOf(0, 4, 7)),
    MINOR("Minor", setOf(0, 3, 7)),
    SIXTH("6th", setOf(0, 4, 7, 9)),
    SEVENTH("7th", setOf(0, 4, 7, 10));

    companion object {
        val ALL = entries.toList()
    }
}

enum class ChordDegree(val label: String, val semitonesFromRoot: Int) {
    I("I", 0),
    FLAT_II("bII", 1),
    II("II", 2),
    FLAT_III("bIII", 3),
    III("III", 4),
    IV("IV", 5),
    FLAT_V("bV", 6),
    V("V", 7),
    FLAT_VI("bVI", 8),
    VI("VI", 9),
    FLAT_VII("bVII", 10),
    VII("VII", 11);

    companion object {
        val ALL = entries.toList()
    }
}
