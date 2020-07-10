package com.supercaliman.note.ui.main.createNote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.supercaliman.note.R
import kotlinx.android.synthetic.main.fragment_note_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.lang.Exception


class NoteCreateFragment : Fragment() {

    private val noteDetailViewModel: NoteCreateViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_date.text = noteDetailViewModel.getDate()
        noteDetailViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { renderErrorUi(it) })



    }

    fun renderErrorUi(e:Exception){
        Timber.e(e)
    }
}