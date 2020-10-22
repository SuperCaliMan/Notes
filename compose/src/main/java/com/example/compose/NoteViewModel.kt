package com.example.compose

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.compose.detail.StateScreen
import com.supercaliman.domain.Note
import com.supercaliman.domain.Result
import com.supercaliman.domain.SingleLiveEvent
import com.supercaliman.domain.UiNote
import com.supercaliman.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.domain.useCase.UpdateNoteTaskUseCase
import kotlinx.coroutines.launch
import java.util.*

class NoteViewModel @ViewModelInject constructor(
    private var getNoteUseCase: GetNoteTaskUseCase,
    private var createNoteUseCase: CreateNoteTaskUseCase,
    private var deleteNoteTaskUseCase: DeleteNoteTaskUseCase,
    private var updateNoteTaskUseCase: UpdateNoteTaskUseCase
) : ViewModel() {
    private val mapper = UiModelMapper()

    private lateinit var noteDetail: Note

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorStatus: LiveData<Exception>
        get() = _errorLiveData

    private val _uiLiveData = MediatorLiveData<List<UiNote>>()
    val uiLiveData: LiveData<List<UiNote>>
        get() = _uiLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingStatus: LiveData<Boolean>
        get() = _loadingLiveData

    fun createNote(uuid: String?, title: String, description: String) {
        val date = Calendar.getInstance().time
        noteDetail = Note(uuid, title = title, description, date)
    }


    private val _stateScreen = MutableLiveData<StateScreen>()
    val stateScreen: LiveData<StateScreen>
        get() = _stateScreen

    fun saveNote() {
        val observable = createNoteUseCase.observe()


        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }


        if (this::noteDetail.isInitialized) {
            val date = Calendar.getInstance().time
            val note = Note(null, noteDetail.title, noteDetail.description, date = date)

            viewModelScope.launch {
                createNoteUseCase.execute(note)
            }
        }


    }

    fun updateNote() {


        val observable = updateNoteTaskUseCase.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }



        viewModelScope.launch {
            val date = Calendar.getInstance().time
            val note = Note(noteDetail.uuid, noteDetail.title, noteDetail.description, date)
            noteDetail.uuid?.let { updateNoteTaskUseCase.execute(note = note) }
        }
    }

    fun getNotesList() {
        val observable = getNoteUseCase.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }

        _uiLiveData.removeSource(observable)
        _uiLiveData.addSource(observable) {
            if (it is Result.Success) {
                _uiLiveData.postValue(
                    it.data.sortedBy { data -> data.date.time }.map { note -> mapper.map(note) }
                )
            }
        }
        viewModelScope.launch { getNoteUseCase.execute() }
    }


    fun setEditMode() = _stateScreen.postValue(StateScreen.EDIT)

    fun prevMode() = _stateScreen.postValue(StateScreen.READ)

    fun delete(uuid: String?) {

        val observable = deleteNoteTaskUseCase.observe()

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
                deleteNoteTaskUseCase.execute(it)
            }
        }
    }
}