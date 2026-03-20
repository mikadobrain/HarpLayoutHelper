package com.harplayouthelper.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.harplayouthelper.app.data.Note
import com.harplayouthelper.app.ui.theme.*
import com.harplayouthelper.app.viewmodel.NoteHighlight

@Composable
fun NoteCell(
    note: Note?,
    highlight: NoteHighlight,
    hasAnyHighlightActive: Boolean,
    displayText: String?,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when {
        note == null -> Color.Transparent
        highlight == NoteHighlight.BOTH -> BothHighlightBg
        highlight == NoteHighlight.CHORD -> ChordHighlightBg
        highlight == NoteHighlight.SCALE -> ScaleHighlightBg
        hasAnyHighlightActive -> Color.White
        else -> Color.White
    }

    val textColor = when {
        highlight == NoteHighlight.BOTH -> BothHighlightText
        highlight == NoteHighlight.CHORD -> ChordHighlightText
        highlight == NoteHighlight.SCALE -> ScaleHighlightText
        hasAnyHighlightActive -> MutedTextColor
        else -> Color.Black
    }

    val fontWeight = when (highlight) {
        NoteHighlight.BOTH, NoteHighlight.CHORD -> FontWeight.Bold
        NoteHighlight.SCALE -> FontWeight.Medium
        NoteHighlight.NONE -> FontWeight.Normal
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(48.dp)
            .height(36.dp)
            .background(backgroundColor)
            .border(0.5.dp, GridBorderColor.copy(alpha = 0.3f))
    ) {
        if (displayText != null) {
            Text(
                text = displayText,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun LabelCell(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(48.dp)
            .height(36.dp)
            .border(0.5.dp, GridBorderColor.copy(alpha = 0.3f))
    ) {
        Text(
            text = text,
            color = LabelColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun HoleNumberCell(
    number: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(48.dp)
            .height(36.dp)
            .background(Color(0xFFE0E0E0))
            .border(0.5.dp, GridBorderColor.copy(alpha = 0.3f))
    ) {
        Text(
            text = number.toString(),
            color = HoleNumberColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}
