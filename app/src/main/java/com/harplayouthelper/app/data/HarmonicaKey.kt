package com.harplayouthelper.app.data

enum class HarmonicaKey(val displayName: String, val semitonesFromC: Int) {
    C("C", 0),
    Db("Db", 1),
    D("D", 2),
    Eb("Eb", 3),
    E("E", 4),
    F("F", 5),
    Gb("Gb", 6),
    G("G", 7),
    Ab("Ab", 8),
    A("A", 9),
    Bb("Bb", 10),
    B("B", 11);

    companion object {
        val ALL = entries.toList()
    }
}
