package com.supercaliman.note.ui.main.ViewModels


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supercaliman.note.ModelMapperTask
import kotlinx.coroutines.launch


class NoteListViewModel @ViewModelInject constructor(private var taskModel: com.supercaliman.core.domain.useCase.GetNoteTaskUseCase) :
    ViewModel() {

    private val mapper = ModelMapperTask()


    private val _errorLiveData = com.supercaliman.core.domain.SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _uiLiveData = MediatorLiveData<List<com.supercaliman.core.domain.UiNote>>()
    val uiLiveData: LiveData<List<com.supercaliman.core.domain.UiNote>>
        get() = _uiLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val LoadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData


    fun getNotesList() {
        val observable = taskModel.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is com.supercaliman.core.domain.Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == com.supercaliman.core.domain.Result.Loading)
        }

        _uiLiveData.removeSource(observable)
        _uiLiveData.addSource(observable) { data ->
            if (data is com.supercaliman.core.domain.Result.Success) {
                _uiLiveData.postValue(
                    data.data.sortedBy { it.date.time }.map { note -> mapper.map(note) }.reversed()
                )
            }
        }


        viewModelScope.launch { taskModel.execute() }
    }


}