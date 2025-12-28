package com.example.figerprintconsole.app.ui.testScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestScreen(
    testViewModel: TestViewModel = viewModel()
) {
    val isLoading by testViewModel.isLoading.collectAsState()
    val userList by testViewModel.userList.collectAsState()

    LaunchedEffect(Unit) {
        testViewModel.onEvent(TestScreenEvent.InitialLoad)
    }

    Scaffold() { innerPadding ->

        Column {

            Spacer(modifier = Modifier.padding(innerPadding))

            Text(text = "Current Users", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp))

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            val userItems = userList
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(userItems) { item ->
                    Text(
                        text = item.name.toString(),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp, 18.dp),
                        maxLines = 1
                    )
                    HorizontalDivider(
                        Modifier,
                        DividerDefaults.Thickness,
                        DividerDefaults.color
                    )
                }
            }

            if(isLoading) {
                Text(text = "Loading User...", color = Color.LightGray, fontSize = 30.sp, modifier = Modifier.align(Alignment.Start))
            }

            Box {
                Button(
                    onClick = {
                        // call our viewModel
                        testViewModel.onEvent(TestScreenEvent.LoadUsers)
                    },
                    colors = ButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContentColor = Color.DarkGray,
                        disabledContainerColor = Color.LightGray
                    ),
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    if(isLoading) {
                        CircularProgressIndicator()
                    } else {
                            Text(text = "Load User", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(innerPadding))

    }
}