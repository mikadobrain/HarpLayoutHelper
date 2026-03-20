package com.harplayouthelper.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.harplayouthelper.app.data.HarmonicaGridData
import com.harplayouthelper.app.data.Note
import com.harplayouthelper.app.ui.theme.*
import com.harplayouthelper.app.viewmodel.NoteHighlight

@Composable
fun HarmonicaGrid(
    gridData: HarmonicaGridData,
    getHighlight: (Note?) -> NoteHighlight,
    getDisplayText: (Note?) -> String?,
    hasAnyHighlightActive: Boolean,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    // Combine bend rows as in the PDF layout
    val bendRowDeep = List(10) { i ->
        if (i < 6) gridData.drawBend3[i] else gridData.blowBend1[i]
    }
    val bendRowMid = List(10) { i ->
        if (i < 6) gridData.drawBend2[i] else gridData.blowBend2[i]
    }
    val bendRowShallow = gridData.drawBend1

    Column(
        modifier = modifier
            .horizontalScroll(scrollState)
            .border(1.dp, GridBorderColor)
    ) {
        // Row 1: OB (Overblow notes holes 1-6, Hole numbers 7-10)
        OverblowHoleRow(
            overblow = gridData.overblow,
            getHighlight = getHighlight,
            getDisplayText = getDisplayText,
            hasAnyHighlightActive = hasAnyHighlightActive,
        )

        // Row 2: Draw
        NoteRow(
            labelLeft = "Draw",
            labelRight = "Draw",
            notes = gridData.draw,
            getHighlight = getHighlight,
            getDisplayText = getDisplayText,
            hasAnyHighlightActive = hasAnyHighlightActive,
            rowBackground = DrawRowTint,
        )

        // Row 3: Draw Bend '
        NoteRow(
            labelLeft = "'",
            labelRight = "",
            notes = bendRowShallow,
            getHighlight = getHighlight,
            getDisplayText = getDisplayText,
            hasAnyHighlightActive = hasAnyHighlightActive,
            rowBackground = BendRowTint,
        )

        // Row 4: Draw Bend '' / Blow Bend ''
        NoteRow(
            labelLeft = "''",
            labelRight = "''",
            notes = bendRowMid,
            getHighlight = getHighlight,
            getDisplayText = getDisplayText,
            hasAnyHighlightActive = hasAnyHighlightActive,
            rowBackground = BendRowTint,
        )

        // Row 5: Draw Bend ''' / Blow Bend '
        NoteRow(
            labelLeft = "'''",
            labelRight = "'",
            notes = bendRowDeep,
            getHighlight = getHighlight,
            getDisplayText = getDisplayText,
            hasAnyHighlightActive = hasAnyHighlightActive,
            rowBackground = BendRowTint,
        )

        // Row 6: Blow
        NoteRow(
            labelLeft = "Blow",
            labelRight = "Blow",
            notes = gridData.blow,
            getHighlight = getHighlight,
            getDisplayText = getDisplayText,
            hasAnyHighlightActive = hasAnyHighlightActive,
            rowBackground = BlowRowTint,
        )

        // Row 7: Hole numbers (1-6), Overdraw notes (7-10)
        HoleOverdrawRow(
            overdraw = gridData.overdraw,
            getHighlight = getHighlight,
            getDisplayText = getDisplayText,
            hasAnyHighlightActive = hasAnyHighlightActive,
        )
    }
}

@Composable
private fun OverblowHoleRow(
    overblow: List<Note?>,
    getHighlight: (Note?) -> NoteHighlight,
    getDisplayText: (Note?) -> String?,
    hasAnyHighlightActive: Boolean,
) {
    Row(modifier = Modifier.background(BendRowTint)) {
        LabelCell(text = "OB")
        for (i in 0 until 6) {
            NoteCell(
                note = overblow[i],
                highlight = getHighlight(overblow[i]),
                hasAnyHighlightActive = hasAnyHighlightActive,
                displayText = getDisplayText(overblow[i]),
            )
        }
        for (i in 7..10) {
            HoleNumberCell(number = i)
        }
        LabelCell(text = "Hole")
    }
}

@Composable
private fun HoleOverdrawRow(
    overdraw: List<Note?>,
    getHighlight: (Note?) -> NoteHighlight,
    getDisplayText: (Note?) -> String?,
    hasAnyHighlightActive: Boolean,
) {
    Row(modifier = Modifier.background(BendRowTint)) {
        LabelCell(text = "Hole")
        for (i in 1..6) {
            HoleNumberCell(number = i)
        }
        for (i in 6 until 10) {
            NoteCell(
                note = overdraw[i],
                highlight = getHighlight(overdraw[i]),
                hasAnyHighlightActive = hasAnyHighlightActive,
                displayText = getDisplayText(overdraw[i]),
            )
        }
        LabelCell(text = "OD")
    }
}

@Composable
private fun NoteRow(
    labelLeft: String,
    labelRight: String,
    notes: List<Note?>,
    getHighlight: (Note?) -> NoteHighlight,
    getDisplayText: (Note?) -> String?,
    hasAnyHighlightActive: Boolean,
    rowBackground: Color,
) {
    Row(modifier = Modifier.background(rowBackground)) {
        LabelCell(text = labelLeft)
        for (note in notes) {
            NoteCell(
                note = note,
                highlight = getHighlight(note),
                hasAnyHighlightActive = hasAnyHighlightActive,
                displayText = getDisplayText(note),
            )
        }
        LabelCell(text = labelRight)
    }
}
