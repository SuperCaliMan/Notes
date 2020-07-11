package com.supercaliman.note.ui.main.createNote


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
import com.supercaliman.domain.SingleLiveEvent
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

    private val formatter = SimpleDateFormat("EEE d, yyyy", Locale.getDefault())
    private val date = Calendar.getInstance().time

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _statusData = MutableLiveData<Boolean>()
    val statusData: LiveData<Boolean>
        get() = _statusData


    fun getDate():String{

        return formatter.format(date).toString()
    }

    fun createNote(title:String,description:String){
        viewModelScope.launch {
             taskModel.execute(Note(null, title, description, date))
        }
    }

}