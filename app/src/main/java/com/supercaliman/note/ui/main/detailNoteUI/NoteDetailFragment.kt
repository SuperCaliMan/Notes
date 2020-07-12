package com.supercaliman.note.ui.main.detailNoteUI

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.supercaliman.note.R
import com.supercaliman.note.ui.main.hideKeyboard
import kotlinx.android.synthetic.main.fragment_note_detail.*
import timber.log.Timber


class NoteDetailFragment : Fragment() {

    private val noteDetailViewModel : NoteDetailViewModel by viewModels()

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
        txt_title.isEnabled = false
        txt_detail.isEnabled = false


        noteDetailViewModel.dataLiveData.observe(viewLifecycleOwner, Observer { Timber.v(it.title) })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                hideKeyboard()
                requireView().findNavController().popBackStack()
            }
            R.id.edit -> {
                txt_detail.isEnabled = true
                txt_title.isEnabled = true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}