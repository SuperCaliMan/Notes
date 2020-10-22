package com.example.compose.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ex.detailScreen
import com.example.compose.NoteViewModel
import com.example.compose.ui.NoteTheme
import com.example.compose.ui.isDarkTheme
import com.supercaliman.domain.UiNote
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var panelMode: StateScreen
    private var uiNote: UiNote? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            panelMode = it.getSerializable(PANEL_MODE) as StateScreen
            it.getSerializable(NOTE)?.let { note ->
                uiNote = note as UiNote
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(context = requireContext()).apply {
            setContent {
                NoteTheme(isDarkTheme(this@DetailFragment.requireContext())) {
                    val viewModel: NoteViewModel by viewModels()
                    detailScreen(
                        viewModel = viewModel,
                        panelMode,
                        backStack = {
                            findNavController().navigateUp()
                        },
                        note = uiNote
                    )
                }
            }
        }
    }


    companion object {
        const val PANEL_MODE = "500"
        const val NOTE = "501"
    }
}