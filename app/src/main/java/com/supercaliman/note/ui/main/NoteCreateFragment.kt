package com.supercaliman.note.ui.main

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.supercaliman.domain.UiNote
import com.supercaliman.note.R
import com.supercaliman.note.hideKeyboard
import com.supercaliman.note.renderErrorUi
import com.supercaliman.note.showKeyBoard
import com.supercaliman.note.ui.main.ViewModels.NoteCreateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteCreateFragment : Fragment() {

    private val noteDetailViewModel: NoteCreateViewModel by viewModels()
    private lateinit var uiNote: UiNote
    private lateinit var txtTitle: TextView
    private lateinit var txtDetail: TextView
    private lateinit var txtDate: TextView


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
        txtTitle = view.findViewById<TextView>(R.id.txt_title);
        txtDetail = view.findViewById<TextView>(R.id.txt_detail)
        txtDate = view.findViewById<TextView>(R.id.txt_date)
        txtDate.text = noteDetailViewModel.getDate()
        txtTitle.requestFocus()
        showKeyBoard()


        noteDetailViewModel.errorLiveData.observe(
            viewLifecycleOwner,
            Observer { it?.let { activity?.renderErrorUi(it) } })

        noteDetailViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
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
                noteDetailViewModel.createNote(txtTitle.text.toString(), txtDetail.text.toString())
            }
            android.R.id.home -> {
                hideKeyboard()
                requireView().findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}