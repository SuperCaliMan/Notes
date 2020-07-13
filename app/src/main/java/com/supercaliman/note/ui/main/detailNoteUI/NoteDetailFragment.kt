package com.supercaliman.note.ui.main.detailNoteUI

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.supercaliman.domain.UiNote
import com.supercaliman.note.R
import com.supercaliman.note.hideKeyboard
import com.supercaliman.note.showDialog
import com.supercaliman.note.showKeyBoard
import com.supercaliman.note.ui.main.createNote.NoteCreateViewModel
import kotlinx.android.synthetic.main.fragment_note_detail.*
import timber.log.Timber


class NoteDetailFragment : Fragment() {

    private val noteDetailViewModel : NoteDetailViewModel by activityViewModels() //use this to get viewModel in Activity-scoped
    private val noteCreateViewModel : NoteCreateViewModel by viewModels()

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        hideKeyboard()
        requireView().findNavController().popBackStack()
        dialog.dismiss()

    }

    private val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        dialog.dismiss()
    }


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

        requireActivity().onBackPressedDispatcher.addCallback(this) {
                onBackClick()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_title.isEnabled = false
        txt_detail.isEnabled = false


        noteDetailViewModel.dataLiveData.observe(viewLifecycleOwner, Observer { it?.let { renderUi(it) } })

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
            android.R.id.home -> { onBackClick() }
            R.id.edit -> { setEditMode() }
            R.id.editSave -> {noteCreateViewModel.createNote(txt_title.text.toString(),txt_detail.text.toString())}
            R.id.delete -> {Timber.v("delete viewModel")}
        }
        return super.onOptionsItemSelected(item)
    }


    private fun renderUi(note:UiNote){
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

    private fun onBackClick(){
        if(isInEditMode()){
            showDialog(requireContext(),"",getString(R.string.alert_msg),positiveButtonClick,negativeButtonClick)
        }else {
            hideKeyboard()
            requireView().findNavController().popBackStack()
        }
    }
}