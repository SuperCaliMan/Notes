package com.example.compose


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import com.example.compose.ui.NoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainComposeActivity : AppCompatActivity() {
    //TODO: https://developer.android.com/training/dependency-injection/hilt-multi-module
    private val viewModel: NoteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNotesList()
        setContent {
            NoteTheme() {
                myApp(viewModel)
            }
        }
    }

    //TODO: https://codelabs.developers.google.com/codelabs/jetpack-compose-state/index.html?index=..%2F..index#3
    @Composable
    fun myApp(viewModel: NoteViewModel) {
        HomeScreen(viewModel) {
            Toast.makeText(applicationContext, it.title, Toast.LENGTH_SHORT).show()
        }
    }


    @Composable
    fun DefaultPreview() {
        NoteTheme {
            //ListScreen("Android")
        }
    }
}

