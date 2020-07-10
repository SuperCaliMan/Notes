package com.supercaliman.note.ui.main.readNotes

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.supercaliman.domain.UiNote
import com.supercaliman.note.R
import com.supercaliman.note.ui.main.AdapterList
import kotlinx.android.synthetic.main.fragment_notes.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.lang.Exception


class NotesFragment : Fragment() {

    private val noteListViewModel: NoteListViewModel by viewModel()
    private lateinit var adapterList: AdapterList




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notelist.layoutManager = LinearLayoutManager(context)
        adapterList = AdapterList()
        notelist.adapter = adapterList

        noteListViewModel.UiLiveData.observe(viewLifecycleOwner, Observer { it?.let { items -> renderUi(items) } })

        noteListViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {  renderErrorUi(it) })

        floatingActionButton.setOnClickListener { view.findNavController().navigate(R.id.action_notesFragment_to_noteDetailFragment) }

    }

    private fun renderUi(data:List<UiNote>){
        if(data.isEmpty()){
            notelist.visibility = View.GONE
        }else{
            adapterList.data = data
        }

    }

    private fun renderErrorUi(e:Exception){
        Timber.e(e)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}