package com.supercaliman.note.ui.main

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.supercaliman.domain.UiNote
import com.supercaliman.note.R
import com.supercaliman.note.renderErrorUi
import com.supercaliman.note.ui.main.ViewModels.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private val noteListViewModel: NoteListViewModel by viewModels()
    private lateinit var notelist: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var emptyList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapterList: AdapterList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notelist = view.findViewById(R.id.notelist)
        fab = view.findViewById(R.id.floatingActionButton)
        emptyList = view.findViewById(R.id.emptylist)
        progressBar = view.findViewById(R.id.progressBar)
        noteListViewModel.getNotesList()

        notelist.layoutManager = LinearLayoutManager(context)
        adapterList = AdapterList()
        notelist.adapter = adapterList

        noteListViewModel.LoadingLiveData.observe(
            viewLifecycleOwner,
            Observer { renderLoadingUi(it) })

        noteListViewModel.uiLiveData.observe(
            viewLifecycleOwner,
            Observer { it?.let { items -> renderUi(items) } })

        noteListViewModel.errorLiveData.observe(
            viewLifecycleOwner,
            Observer { activity?.renderErrorUi(it) })

        fab.setOnClickListener {
            view.findNavController().navigate(R.id.action_notesFragment_to_noteCreateFragment)
        }

    }

    /*
    override fun getObjClicked(data: UiNote) {
        setFragmentResult("data", bundleOf("data" to data))
        requireView().findNavController().navigate(R.id.action_notesFragment_to_detailFragment)
    }
     */


    private fun renderUi(data:List<UiNote>){
        if(data.isEmpty()){
            notelist.visibility = View.GONE
            emptyList.visibility = View.VISIBLE
        }else{
            notelist.visibility = View.VISIBLE
            emptyList.visibility = View.GONE
            adapterList.data = data
        }

    }

    private fun renderLoadingUi(isLoading:Boolean){
        if(isLoading){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}