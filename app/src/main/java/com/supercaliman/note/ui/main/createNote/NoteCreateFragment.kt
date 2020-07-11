package com.supercaliman.note.ui.main.createNote

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.supercaliman.note.R
import com.supercaliman.note.ui.main.readNotes.NoteListViewModel
import kotlinx.android.synthetic.main.fragment_note_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.getOrCreateScope
import timber.log.Timber
import java.lang.Exception


class NoteCreateFragment : Fragment() {

    private val noteDetailViewModel: NoteCreateViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_date.text = noteDetailViewModel.getDate()
        noteDetailViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { it?.let { renderErrorUi(it) } })

        noteDetailViewModel.statusData.observe(viewLifecycleOwner, Observer { it?.let {
            if(it){
                view.findNavController().popBackStack()
            }}
        })
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.save -> { noteDetailViewModel.createNote(txt_title.text.toString(),txt_detail.text.toString()) }
            android.R.id.home -> {requireView().findNavController().popBackStack()}
        }
        return super.onOptionsItemSelected(item)
    }

    fun renderErrorUi(e:Exception){
        Timber.e(e)
    }
}