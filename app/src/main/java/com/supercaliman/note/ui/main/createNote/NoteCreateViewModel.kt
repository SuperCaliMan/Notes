package com.supercaliman.note.ui.main.createNote

import SingleLiveEvent
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supercaliman.domain.Note
import com.supercaliman.domain.Result
import com.supercaliman.domain.UiNote
import com.supercaliman.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.note.ModelMapperTask
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class NoteCreateViewModel(private var taskModel:CreateNoteTaskUseCase) : ViewModel() {

    private var formatter = SimpleDateFormat("EEE d, yyyy", Locale.getDefault())

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData


    fun getDate():String{
        val date = Calendar.getInstance().time
        return formatter.format(date).toString()
    }

    fun createNote(){
        viewModelScope.launch {
             val res =  taskModel.execute(
                    Note(
                    null,
                    "titolo android",
                "descrizione",
                    Date(2020,7,10)
                )
            )

            when(res){
                is Result.Error -> _errorLiveData.postValue(res.exception)
                is Result.Success -> print("ok")
            }
        }
    }

}