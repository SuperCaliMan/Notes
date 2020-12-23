package com.supercaliman.core

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.supercaliman.core.base.BaseViewModel
import com.supercaliman.core.domain.*
import com.supercaliman.core.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.core.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.core.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.core.domain.useCase.UpdateNoteTaskUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel @ViewModelInject constructor(
    private var getNoteTaskUseCase: GetNoteTaskUseCase,
    private var createNoteTaskUseCase: CreateNoteTaskUseCase,
    private var deleteTaskModel: DeleteNoteTaskUseCase,
    private var updateTaskModel: UpdateNoteTaskUseCase
) : BaseViewModel() {

    private val mapper = ModelMapperTask()
    private val formatter = SimpleDateFormat("EEE d, yyyy", Locale.getDefault())
    private val date = Calendar.getInstance().time


    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _uiLiveData = MediatorLiveData<List<UiNote>>()
    val uiLiveData: LiveData<List<UiNote>>
        get() = _uiLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData


    fun getNotesList() {
        val observable = getNoteTaskUseCase.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }

        _uiLiveData.removeSource(observable)
        _uiLiveData.addSource(observable) { data ->
            if (data is Result.Success) {
                _uiLiveData.postValue(
                    data.data.sortedBy { it.date.time }.map { note -> mapper.map(note) }.reversed()
                )
            }
        }


        viewModelScope.launch { getNoteTaskUseCase.execute() }
    }


    fun getDate(): String {

        return formatter.format(date).toString()
    }

    fun createNote(title: String, description: String) {
        val observable = createNoteTaskUseCase.observe()

        _errorLiveData.removeSource(observable)
        _loadingLiveData.removeSource(observable)

        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }



        viewModelScope.launch {
            createNoteTaskUseCase.execute(Note(null, title, description, date))
        }
    }


    fun delete(uuid: String?) {
        val observable = deleteTaskModel.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }



        viewModelScope.launch {
            uuid?.let { deleteTaskModel.execute(uuid) }
        }
    }

    fun update(title: String, detail: String, uuid: String?) {
        val date = Calendar.getInstance().time

        val observable = updateTaskModel.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }



        viewModelScope.launch {
            uuid?.let {
                updateTaskModel.execute(
                    Note(
                        uuid,
                        title,
                        detail,
                        date
                    )
                )
            }
        }
    }
}