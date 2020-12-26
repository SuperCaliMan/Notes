package com.supercaliman.note.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.supercaliman.analytics.SegmentHelper
import com.supercaliman.core.*
import com.supercaliman.note.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_detail.*
import javax.inject.Inject


@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private lateinit var uiNote: com.supercaliman.core.domain.UiNote
    private val detailNoteViewModel: NoteViewModel by viewModels()

    @Inject
    lateinit var segment: SegmentHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) { onBackPress() }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_title.isEnabled = false
        txt_detail.isEnabled = false

        setFragmentResultListener("data") { _, bundle ->
            val res = bundle.getSerializable("data") as com.supercaliman.core.domain.UiNote
            renderUi(res)
        }

        segment.trackScreen(com.supercaliman.analytics.TrackActions.detailOpen)
        //detailNoteViewModel.loadingLiveData.observe(viewLifecycleOwner, { })

        detailNoteViewModel.errorLiveData.observe(
            viewLifecycleOwner,
            Observer { activity?.renderErrorUi(it) })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(isInEditMode()){
            inflater.inflate(R.menu.detail_option_menu_edit,menu)
        }else{
            inflater.inflate(R.menu.detail_option_menu,menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                segment.trackEvent(com.supercaliman.analytics.TrackActions.detailGoToHome)
                onBackPress()
            }
            R.id.edit -> {
                segment.trackEvent(com.supercaliman.analytics.TrackActions.detailInEditMode)
                setEditMode()
            }
            R.id.editSave -> {
                segment.trackEvent(
                    com.supercaliman.analytics.TrackActions.saveNote, mapOf(
                        "title" to txt_title.text.toString(),
                        "description" to txt_detail.text.toString()
                    )
                )
                detailNoteViewModel.update(
                    txt_title.text.toString(),
                    txt_detail.text.toString(),
                    uiNote.uuid
                )
                hideKeyboard()
                requireView().findNavController().popBackStack()
            }
            R.id.delete -> {
                segment.trackEvent(
                    com.supercaliman.analytics.TrackActions.deleteNote,
                    mapOf("uuid" to uiNote.uuid!!)
                )
                detailNoteViewModel.delete(uiNote.uuid)
                hideKeyboard()
                requireView().findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun renderUi(note: com.supercaliman.core.domain.UiNote) {
        uiNote = note
        txt_title.setText(note.title)
        txt_date.text = note.date
        txt_detail.setText(note.description)
    }


    private fun isInEditMode():Boolean = txt_title.isEnabled && txt_detail.isEnabled

    private fun setEditMode(){
        txt_detail.isEnabled = true
        txt_title.isEnabled = true
        txt_title.requestFocus()
        requireActivity().invalidateOptionsMenu()
        showKeyBoard()
    }

    private fun onBackPress() {
        val positiveButtonClick = { dialog: DialogInterface, _: Int ->
            hideKeyboard()
            requireView().findNavController().popBackStack()
            dialog.dismiss()
        }

        val negativeButtonClick = { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        if(isInEditMode()){
            showDialog(requireContext(),"",getString(R.string.alert_msg),positiveButtonClick,negativeButtonClick)
        }else {
            hideKeyboard()
            requireView().findNavController().popBackStack()
        }
    }
}