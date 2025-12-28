package com.example.figerprintconsole.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

// We will practice Scaffold + LaunchedEffect + remember + mutableStateOf
@OptIn(ExperimentalMaterial3Api::class)
// @Preview(showBackground = true)
@Composable
fun PracticeScaffold(

) {

    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    Color.LightGray
                )
            )
        },

        bottomBar = {
            BottomAppBar(
                containerColor = Color.LightGray
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("A", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text("B", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text("C", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                }
            }
        },

        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }

    ) {  innerPadding ->
        Text("Hello there", modifier = Modifier.padding(innerPadding))

        // Trigger SnackBar without using the LaunchedEffect
        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(message = "Hi Friend")
        }
    }
}

// A little Danger
@Composable
fun PracticeLaunchedEffect(

) {
    /// println("Hello There")
    Text("Hello There", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
}

@Preview(showBackground = true)
@Composable
fun ButtonIncrement() {
    // mutableStateOf holds the value and notifies composable when it's value changes
    // remember help to remember value between the coroutines
    var count by remember { mutableIntStateOf(0) }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Count Value = $count")

            Spacer(modifier = Modifier.padding(innerPadding))

            Button( onClick = {
                count += 1
            }) {
                Text("Increment")
            }
        }
    }
}