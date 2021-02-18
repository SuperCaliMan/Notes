package com.example.compose


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.UiNote
import com.example.compose.domain.UiNoteMapper
import com.example.compose.domain.useCase.*
import com.example.compose.home.EventHome
import com.supercaliman.core.base.BaseViewModel
import com.supercaliman.core.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


class NoteViewModel @ViewModelInject constructor(
    private var getNoteUseCase: GetNoteTaskUseCase,
    private var createNoteUseCase: CreateNoteTaskUseCase,
    private var deleteNoteTaskUseCase: DeleteNoteTaskUseCase,
    private var updateNoteTaskUseCase: UpdateNoteTaskUseCase,
    private var userOperationsUseCase: UserOperationsUseCase,
    private var uiNoteMapper: UiNoteMapper
) : BaseViewModel() {

    private lateinit var noteDetail: UiNote

    private lateinit var _noteList: List<UiNote>

    private val _uiNoteList = MediatorLiveData<List<UiNote>>()
    val uiNoteList: LiveData<List<UiNote>>
        get() = _uiNoteList

    private val _homeState = MutableLiveData<EventHome>()
    val homeState: LiveData<EventHome>
        get() = _homeState

    fun createNote(uuid: String?, title: String, description: String) {
        noteDetail = UiNote(uuid, title, description, "")
    }

    fun getNote(uuid: String?): UiNote? {
        return if (uuid != null) {
            _noteList.find { it.uuid.equals(uuid) }
        } else {
            null
        }
    }

    fun saveNote() {
        val observable = createNoteUseCase.observe()

        _error.removeSource(observable)
        _error.addSource(observable) {
            if (it is Result.Error) _error.postValue(it.exception)
        }

        _loader.removeSource(observable)
        _loader.addSource(observable) {
            _loader.postValue(it == Result.Loading)
        }


        if (this::noteDetail.isInitialized) {
            viewModelScope.launch(Dispatchers.IO) {
                createNoteUseCase.execute(
                    uiNoteMapper.toDTOModel(
                        noteDetail,
                        userOperationsUseCase.getUser()!!
                    )
                )
            }
        }


    }

    fun updateNote() {
        val observable = updateNoteTaskUseCase.observe()

        _error.removeSource(observable)
        _error.addSource(observable) {
            if (it is Result.Error) _error.postValue(it.exception)
        }

        _loader.removeSource(observable)
        _loader.addSource(observable) {
            _loader.postValue(it == Result.Loading)
        }

        viewModelScope.launch(Dispatchers.IO) {
            noteDetail.uuid?.let {
                updateNoteTaskUseCase.execute(
                    uiNoteMapper.toDTOModel(
                        noteDetail,
                        userOperationsUseCase.getUser()!!
                    )
                )
            }
        }
    }


    @InternalCoroutinesApi
    fun getNotesList() {
        val observable = getNoteUseCase.observe()

        _error.removeSource(observable)
        _error.addSource(observable) {
            if (it is Result.Error) _error.postValue(it.exception)
        }

        _loader.removeSource(observable)
        _loader.addSource(observable) {
            _loader.postValue(it == Result.Loading)
        }

        _uiNoteList.removeSource(observable)
        _uiNoteList.addSource(observable) {
            if (it is Result.Success) {
                _noteList = it.data.map { note -> uiNoteMapper.toUiModel(note) }
                _uiNoteList.postValue(_noteList)

            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getNoteUseCase.getNotes()
        }
    }

    fun delete(uuid: String?) {
        val observable = deleteNoteTaskUseCase.observe()

        _error.removeSource(observable)
        _error.addSource(observable) {
            if (it is Result.Error) _error.postValue(it.exception)
        }

        _loader.removeSource(observable)
        _loader.addSource(observable) {
            _loader.postValue(it == Result.Loading)
        }



        viewModelScope.launch(Dispatchers.IO) {
            uuid?.let {
                deleteNoteTaskUseCase.execute(it)
            }
        }
    }


    fun logout() {
        _homeState.postValue(EventHome.LOGOUT)
    }
}