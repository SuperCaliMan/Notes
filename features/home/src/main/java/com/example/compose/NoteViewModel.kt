package com.example.compose


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.UiNote
import com.example.compose.domain.UiNoteMapper
import com.supercaliman.core.base.BaseViewModel
import com.supercaliman.core.data.CoreRepository
import com.supercaliman.core.domain.Result
import com.supercaliman.core.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.core.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.core.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.core.domain.useCase.UpdateNoteTaskUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


class NoteViewModel @ViewModelInject constructor(
    private var getNoteUseCase: GetNoteTaskUseCase,
    private var createNoteUseCase: CreateNoteTaskUseCase,
    private var deleteNoteTaskUseCase: DeleteNoteTaskUseCase,
    private var updateNoteTaskUseCase: UpdateNoteTaskUseCase,
    var coreRepository: CoreRepository,
    private var uiNoteMapper: UiNoteMapper
) : BaseViewModel() {

    private lateinit var noteDetail: UiNote

    private lateinit var _noteList: List<UiNote>

    private val _uiNoteList = MediatorLiveData<List<UiNote>>()
    val uiNoteList: LiveData<List<UiNote>>
        get() = _uiNoteList

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
            viewModelScope.launch {
                createNoteUseCase.execute(
                    uiNoteMapper.toDTOModel(
                        noteDetail,
                        coreRepository.currentUser!!
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

        viewModelScope.launch {
            noteDetail.uuid?.let {
                updateNoteTaskUseCase.execute(
                    uiNoteMapper.toDTOModel(
                        noteDetail,
                        coreRepository.currentUser!!
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
        viewModelScope.launch {
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



        viewModelScope.launch {
            uuid?.let {
                deleteNoteTaskUseCase.execute(it)
            }
        }
    }
}