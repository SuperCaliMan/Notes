package com.supercaliman.note.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.supercaliman.note.BindingRecycleView
import com.supercaliman.note.R
import com.supercaliman.note.renderErrorUi
import com.supercaliman.note.ui.main.ViewModels.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notes.*
import javax.inject.Inject


@AndroidEntryPoint
class NotesFragment : Fragment(), BindingRecycleView<com.supercaliman.core.domain.UiNote> {

    private val noteListViewModel: NoteListViewModel by viewModels()
    private lateinit var adapterList: AdapterList

    @Inject
    lateinit var segment: com.supercaliman.analytics.SegmentHelper

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

        segment.trackScreen(com.supercaliman.analytics.TrackActions.homeOpen)

        noteListViewModel.getNotesList()

        notelist.layoutManager = LinearLayoutManager(context)
        adapterList = AdapterList(this)
        notelist.adapter = adapterList

        noteListViewModel.LoadingLiveData.observe(viewLifecycleOwner) { renderLoadingUi(it) }

        noteListViewModel.uiLiveData.observe(viewLifecycleOwner) { renderUi(it) }

        noteListViewModel.errorLiveData.observe(viewLifecycleOwner) { activity?.renderErrorUi(it) }

        floatingActionButton.setOnClickListener {
            segment.trackEvent(com.supercaliman.analytics.TrackActions.createNewNote)
            view.findNavController().navigate(R.id.action_notesFragment_to_noteCreateFragment)
        }

    }


    override fun getObjClicked(data: com.supercaliman.core.domain.UiNote) {
        setFragmentResult("data", bundleOf("data" to data))
        segment.trackEvent(
            com.supercaliman.analytics.TrackActions.goToDetail,
            mapOf("uuid" to data.uuid!!)
        )
        requireView().findNavController().navigate(R.id.action_notesFragment_to_detailFragment)
    }


    private fun renderUi(data: List<com.supercaliman.core.domain.UiNote>) {
        if (data.isEmpty()) {
            notelist.visibility = View.GONE
            emptylist.visibility = View.VISIBLE
        } else {
            notelist.visibility = View.VISIBLE
            emptylist.visibility = View.GONE
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