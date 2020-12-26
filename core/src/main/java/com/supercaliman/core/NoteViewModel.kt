package com.supercaliman.core


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supercaliman.core.domain.*
import com.supercaliman.core.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.core.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.core.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.core.domain.useCase.UpdateNoteTaskUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*


class NoteViewModel @ViewModelInject constructor(
    private var getNoteUseCase: GetNoteTaskUseCase,
    private var createNoteUseCase: CreateNoteTaskUseCase,
    private var deleteNoteTaskUseCase: DeleteNoteTaskUseCase,
    private var updateNoteTaskUseCase: UpdateNoteTaskUseCase,
) : ViewModel() {
    private val mapper = UiModelMapper()

    private lateinit var noteDetail: Note

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorStatus: LiveData<Exception>
        get() = _errorLiveData

    private val _uiLiveData = MediatorLiveData<List<UiNote>>()
    val uiLiveData: LiveData<List<UiNote>>
        get() = _uiLiveData

    private val _uiNote = MediatorLiveData<UiNote>()
    val uiNote: LiveData<UiNote>
        get() = _uiNote

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingStatus: LiveData<Boolean>
        get() = _loadingLiveData

    fun createNote(uuid: String?, title: String, description: String) {
        val date = Calendar.getInstance().time
        noteDetail = Note(uuid, title = title, description, date)
    }

    fun getNote(uuid: String?) {
        val observable = getNoteUseCase.observeNote()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable) {
            if (it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable) {
            _loadingLiveData.postValue(it == Result.Loading)
        }

        _uiNote.removeSource(observable)
        _uiNote.addSource(observable) {
            if (it is Result.Success) {
                _uiNote.postValue(mapper.map(it.data))
            }
        }

        uuid?.let { id ->
            viewModelScope.launch {
                getNoteUseCase.getNote(id)
            }
        }

    }

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
            noteDetail.date = Calendar.getInstance().time
            val note = noteDetail
            noteDetail.uuid?.let { updateNoteTaskUseCase.execute(note = note) }
        }
    }


    @InternalCoroutinesApi
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
                    it.data.sortedBy { data -> data.date.time }
                        .map { note -> mapper.map(note) }
                        .reversed())
            }
        }
        viewModelScope.launch { getNoteUseCase.getNotes() }
    }

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