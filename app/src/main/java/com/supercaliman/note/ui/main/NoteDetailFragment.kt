package com.supercaliman.note.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.supercaliman.domain.UiNote
import com.supercaliman.note.*
import com.supercaliman.note.ui.main.ViewModels.DetailNoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    //private val sharedViewModel : SharedViewModel by viewModels() //use this to shared view model in different fragment
    private lateinit var uiNote: UiNote
    private lateinit var txtTitle: TextView
    private lateinit var txtDetail: TextView
    private lateinit var txtDate: TextView


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
        requireActivity().onBackPressedDispatcher.addCallback(this) { onBackPress() }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtTitle = view.findViewById<TextView>(R.id.txt_title);
        txtDetail = view.findViewById<TextView>(R.id.txt_detail)
        txtDate = view.findViewById<TextView>(R.id.txt_date)
        txtTitle.isEnabled = false
        txtDetail.isEnabled = false

        setFragmentResultListener("data") { _, bundle ->
            val res = bundle.getSerializable("data") as UiNote
            renderUi(res)
        }



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
            android.R.id.home -> {
                onBackPress()
            }
            R.id.edit -> {
                setEditMode()
            }
            R.id.editSave -> {
                detailNoteViewModel.update(
                    txtTitle.text.toString(),
                    txtDetail.text.toString(),
                    uiNote.uuid
                )
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


    private fun renderUi(note: UiNote) {
        uiNote = note
        txtTitle.text = note.title
        txtDate.text = note.date
        txtDetail.text = note.description
    }


    private fun isInEditMode(): Boolean = txtTitle.isEnabled && txtDetail.isEnabled

    private fun setEditMode() {
        txtDetail.isEnabled = true
        txtTitle.isEnabled = true
        txtTitle.requestFocus()
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