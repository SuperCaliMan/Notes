package com.example.compose.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.compose.NoteViewModel
import com.example.compose.R
import com.example.compose.detail.DetailFragment
import com.example.compose.detail.StateScreen
import com.example.compose.ui.NoteTheme
import com.example.compose.ui.isDarkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(context = requireContext()).apply {
            setContent {
                NoteTheme(isDarkTheme(this@HomeFragment.requireContext())) {
                    val viewModel: NoteViewModel by viewModels()
                    viewModel.getNotesList()
                    myApp(viewModel)
                }
            }
        }
    }


    @Composable
    fun myApp(viewModel: NoteViewModel) {
        homeScreen(
            viewModel = viewModel,
            onItemClick = {
                val bundle = bundleOf(DetailFragment.PANEL_MODE to StateScreen.READ)
                bundle.putSerializable(DetailFragment.NOTE, it)
                findNavController().navigate(
                    R.id.detailFragment,
                    bundle
                )
            },
            fabAction = {
                val bundle = bundleOf(DetailFragment.PANEL_MODE to StateScreen.INSERT)
                findNavController().navigate(
                    R.id.detailFragment,
                    bundle
                )
            })
    }


}
