package com.supercaliman.note.ui.main.readNotes

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supercaliman.domain.Result
import com.supercaliman.domain.UiNote
import com.supercaliman.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.note.ModelMapperTask
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class NoteListViewModel(private var taskModel:GetNoteTaskUseCase) : ViewModel() {

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _UiLiveData = MutableLiveData<List<UiNote>>()
    val UiLiveData: LiveData<List<UiNote>>
        get() = _UiLiveData

    private var mapper = ModelMapperTask()



    init {
        viewModelScope.launch {
            val res = taskModel.execute()
            Timber.v(res.toString())
            when(res){
                is Result.Success -> _UiLiveData.postValue(
                   res.data.map { mapper.map(it) }
                )
                is Result.Error -> _errorLiveData.postValue(res.exception)
            }
        }
    }

}