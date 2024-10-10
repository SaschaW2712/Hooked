package com.saschaw.hooked

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.saschaw.hooked.core.authentication.AuthenticationManager
import com.saschaw.hooked.core.datastore.PreferencesDataSource
import com.saschaw.hooked.core.designsystem.theme.HookedTheme
import com.saschaw.hooked.ui.HookedApp
import com.saschaw.hooked.ui.rememberHookedAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferences: PreferencesDataSource

    @Inject
    lateinit var authenticationManager: AuthenticationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            LaunchedEffect(Unit) {
                preferences.initUserData()
            }

            enableEdgeToEdge()

            val appState = rememberHookedAppState(authenticationManager, preferences)

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
    // TODO: Figure out how to spoof preferences
//    HookedApp(rememberHookedAppState())
}