package com.supercaliman.note.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.supercaliman.domain.UiNote
import com.supercaliman.note.BindingRecycleView
import com.supercaliman.note.R
import com.supercaliman.note.renderErrorUi
import com.supercaliman.note.ui.main.ViewModels.NoteListViewModel
import com.supercaliman.note.ui.main.ViewModels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_notes.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class NotesFragment : Fragment(),BindingRecycleView {

    private val noteListViewModel: NoteListViewModel by viewModel()
    val sharedViewModel: SharedViewModel by sharedViewModel()
    private lateinit var adapterList: AdapterList




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notelist.layoutManager = LinearLayoutManager(context)
        adapterList = AdapterList(this)
        notelist.adapter = adapterList

        noteListViewModel.LoadingLiveData.observe(viewLifecycleOwner, Observer { renderLoadingUi(it) })

        noteListViewModel.uiLiveData.observe(viewLifecycleOwner, Observer { it?.let { items -> renderUi(items) } })

        noteListViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {  activity?.renderErrorUi(it) })

        floatingActionButton.setOnClickListener { view.findNavController().navigate(R.id.action_notesFragment_to_noteDetailFragment) }

    }


    override fun getObjClicked(data: UiNote) {
        sharedViewModel.showDetail(data)
        requireView().findNavController().navigate(R.id.action_notesFragment_to_noteDetailFragment2)
    }

    override fun onItemClicked(position: Int) {

    }


    override fun onResume() {
        super.onResume()
        noteListViewModel.getNotesList()
    }



    private fun renderUi(data:List<UiNote>){
        if(data.isEmpty()){
            notelist.visibility = View.GONE
            emptylist.visibility = View.VISIBLE
        }else{
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