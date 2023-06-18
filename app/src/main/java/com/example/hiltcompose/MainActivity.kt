package com.example.hiltcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hiltcompose.ui.theme.HiltComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val state = viewModel.state.collectAsStateWithLifecycle(lifecycleOwner)

    MainView(
        state = state.value,
        updateSavedText = {
            viewModel.setSavedText(it)
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainView(
    state: MainState,
    updateSavedText: (String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val width = 250.dp
    var text by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = state.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Saved Text",
            fontWeight = FontWeight.Medium
        )

        Text(
            text = state.savedText
        )

        Spacer(modifier = Modifier.height(64.dp))

        OutlinedTextField(
            singleLine = true,
            value = text,
            onValueChange = {
                text = it
            },
            label = {
                Text(
                    text = "New Text"
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    updateSavedText(text)
                    text = ""
                }
            ),
            modifier = Modifier.width(width)
        )

        Button(
            shape = RoundedCornerShape(4.dp),
            onClick = {
                updateSavedText(text)
                text = ""
            },
            modifier = Modifier.width(width)
        ) {
            Text(
                text = "Update Saved Text"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainView() {
    val state = MainState(
        title = "App Name",
        savedText = "Test"
    )

    HiltComposeTheme {
        MainView(
            state = state,
            updateSavedText = {}
        )
    }
}