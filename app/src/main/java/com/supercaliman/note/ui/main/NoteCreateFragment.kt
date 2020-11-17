package com.supercaliman.note.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.supercaliman.core.SegmentHelper
import com.supercaliman.core.TrackActions
import com.supercaliman.note.R
import com.supercaliman.note.hideKeyboard
import com.supercaliman.note.renderErrorUi
import com.supercaliman.note.showKeyBoard
import com.supercaliman.note.ui.main.ViewModels.NoteCreateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class NoteCreateFragment : Fragment() {

    private val noteDetailViewModel: NoteCreateViewModel by viewModels()

    @Inject
    lateinit var segmentHelper: SegmentHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_date.text = noteDetailViewModel.getDate()
        txt_title.requestFocus()
        showKeyBoard()
        segmentHelper.trackScreen(TrackActions.createOpen)

        noteDetailViewModel.errorLiveData.observe(
            viewLifecycleOwner,
            { it?.let { activity?.renderErrorUi(it) } })

        noteDetailViewModel.loadingLiveData.observe(viewLifecycleOwner, {
            if (it) {
                hideKeyboard()
                view.findNavController().popBackStack()
            }
        })
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save -> {
                hideKeyboard()
                segmentHelper.trackEvent(
                    TrackActions.saveNote,
                    mapOf(
                        "title" to txt_title.text.toString(),
                        "description" to txt_detail.text.toString()
                    )
                )
                noteDetailViewModel.createNote(
                    txt_title.text.toString(),
                    txt_detail.text.toString()
                )
            }
            android.R.id.home -> {
                hideKeyboard()
                segmentHelper.trackEvent(TrackActions.createToHome)
                requireView().findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}