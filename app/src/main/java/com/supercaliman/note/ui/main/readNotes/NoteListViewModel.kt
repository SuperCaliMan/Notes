package com.supercaliman.note.ui.main.readNotes


import androidx.lifecycle.*
import com.supercaliman.domain.Result
import com.supercaliman.domain.SingleLiveEvent
import com.supercaliman.domain.UiNote
import com.supercaliman.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.note.ModelMapperTask
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteListViewModel(private var taskModel:GetNoteTaskUseCase) : ViewModel() {

    private val mapper = ModelMapperTask()

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _uiLiveData = MediatorLiveData<List<UiNote>>()
    val UiLiveData: LiveData<List<UiNote>>
        get() = _uiLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val LoadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData




    fun getNotesList() {

        val observable = taskModel.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable){
            if(it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable){
            _loadingLiveData.postValue(it == Result.Loading)
        }

        _uiLiveData.removeSource(observable)
        _uiLiveData.addSource(observable){
            if( it is Result.Success){
                _uiLiveData.postValue(
                    mapper.sortUiList(it.data).map { note -> mapper.map(note)}
                )
            }
        }

        viewModelScope.launch { taskModel.execute() }
    }


}