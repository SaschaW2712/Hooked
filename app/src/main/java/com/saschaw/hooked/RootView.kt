package com.saschaw.hooked

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Suppress("ktlint:standard:function-naming")
@Composable
fun RootView() {
//    AppTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()

        var showContent by remember { mutableStateOf(false) }
        Scaffold {
            Column(
                Modifier.padding(it).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { "hello" }

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("Compose: $greeting")
                    }
                }
            }
        }
    }
//    }
}
