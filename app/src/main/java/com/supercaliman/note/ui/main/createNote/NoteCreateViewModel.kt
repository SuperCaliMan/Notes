package com.supercaliman.note.ui.main.createNote


import androidx.lifecycle.*
import com.supercaliman.domain.Note
import com.supercaliman.domain.Result
import com.supercaliman.domain.SingleLiveEvent
import com.supercaliman.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.domain.useCase.GetNoteTaskUseCase
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NoteCreateViewModel(private var taskModel:CreateNoteTaskUseCase) : ViewModel() {

    private val formatter = SimpleDateFormat("EEE d, yyyy", Locale.getDefault())
    private val date = Calendar.getInstance().time

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData


    fun getDate():String{

        return formatter.format(date).toString()
    }

    fun createNote(title:String,description:String){
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

}