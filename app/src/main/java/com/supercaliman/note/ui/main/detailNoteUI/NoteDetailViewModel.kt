package com.supercaliman.note.ui.main.detailNoteUI

import androidx.lifecycle.*
import com.supercaliman.domain.Note
import com.supercaliman.domain.Result
import com.supercaliman.domain.SingleLiveEvent
import com.supercaliman.domain.UiNote
import com.supercaliman.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.domain.useCase.UpdateNoteTaskUseCase
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailViewModel(private val updateNoteTaskUseCase: UpdateNoteTaskUseCase,private val deleteNoteTaskUseCase: DeleteNoteTaskUseCase): ViewModel() {

    /*

    private val formatter = SimpleDateFormat("EEE d, yyyy", Locale.getDefault())
    private val date = Calendar.getInstance().time

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _dataLiveData = MediatorLiveData<UiNote>()
    val dataLiveData: LiveData<UiNote>
        get() = _dataLiveData

     */


    fun update(title:String,description:String){
        viewModelScope.launch {
            updateNoteTaskUseCase.execute(Note("id",title,description,Calendar.getInstance().time))
        }
    }


    fun delete(note:UiNote?){
        let{
            viewModelScope.launch {
                deleteNoteTaskUseCase.execute(note?.uuid!!)
            }
        }
    }

    init {

        /*
      val observable = taskModel.observe()

      _errorLiveData.removeSource(observable)
      _loadingLiveData.removeSource(observable)

      _errorLiveData.addSource(observable){
          if(it is Result.Error) _errorLiveData.postValue(it.exception)
      }

      _loadingLiveData.addSource(observable){
          _loadingLiveData.postValue(it == Result.Loading)
      }


      viewModelScope.launch {
          taskModel.execute(Note(null, title, description, date))
      }
    }

         */
    }


}