package com.harplayouthelper.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.harplayouthelper.app.ui.HarmonicaScreen
import com.harplayouthelper.app.ui.theme.HarpLayoutHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarpLayoutHelperTheme {
                HarmonicaScreen()
            }
        }
    }
}
