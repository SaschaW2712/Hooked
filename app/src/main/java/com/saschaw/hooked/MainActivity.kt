package com.saschaw.hooked

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.saschaw.hooked.core.designsystem.theme.HookedTheme
import com.saschaw.hooked.ui.HookedApp
import com.saschaw.hooked.ui.rememberHookedAppState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()


        setContent {
            enableEdgeToEdge()

            val appState = rememberHookedAppState()

            HookedTheme {
                HookedApp(appState)
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun AppAndroid_Preview() {
    HookedApp(rememberHookedAppState())
}