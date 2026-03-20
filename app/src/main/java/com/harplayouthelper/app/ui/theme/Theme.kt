package com.harplayouthelper.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Chord highlight: strong blue
val ChordHighlightBg = Color(0xFF1565C0)
val ChordHighlightText = Color.White

// Scale highlight: pale green/teal (blasser)
val ScaleHighlightBg = Color(0xFFB2DFDB)
val ScaleHighlightText = Color(0xFF004D40)

// Both scale + chord: chord color dominates
val BothHighlightBg = Color(0xFF0D47A1)
val BothHighlightText = Color.White

val MutedTextColor = Color(0xFF9E9E9E)
val DrawRowTint = Color(0xFFF3F8FF)
val BlowRowTint = Color(0xFFFFF3F0)
val BendRowTint = Color(0xFFF9F9F9)
val GridBorderColor = Color(0xFF424242)
val LabelColor = Color(0xFF616161)
val HoleNumberColor = Color(0xFF212121)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.White,
    surface = Color(0xFFFAFAFA),
    onSurface = Color(0xFF212121),
)

@Composable
fun HarpLayoutHelperTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content,
    )
}
