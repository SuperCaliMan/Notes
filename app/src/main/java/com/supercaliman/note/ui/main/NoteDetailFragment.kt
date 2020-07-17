package com.supercaliman.note.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.supercaliman.domain.UiNote
import com.supercaliman.note.*
import com.supercaliman.note.ui.main.ViewModels.DetailNoteViewModel
import com.supercaliman.note.ui.main.ViewModels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_note_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NoteDetailFragment : Fragment() {

    private val sharedViewModel : SharedViewModel by sharedViewModel() //use this to shared view model in different fragment
    private lateinit var uiNote: UiNote
    private val detailNoteViewModel: DetailNoteViewModel by viewModels()




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

        requireActivity().onBackPressedDispatcher.addCallback(this) { onBackClick() }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_title.isEnabled = false
        txt_detail.isEnabled = false


        sharedViewModel.dataLiveData.observe(
            viewLifecycleOwner,
            Observer { it?.let { renderUi(it) } })

        detailNoteViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer { })

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
            android.R.id.home -> { onBackClick() }
            R.id.edit -> { setEditMode() }
            R.id.editSave -> {
                detailNoteViewModel.update(txt_title.text.toString(),txt_detail.text.toString(),uiNote.uuid)
                hideKeyboard()
                requireView().findNavController().popBackStack()
            }
            R.id.delete -> {
                detailNoteViewModel.delete(uiNote.uuid)
                hideKeyboard()
                requireView().findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun renderUi(note:UiNote){
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

    private fun onBackClick(){
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