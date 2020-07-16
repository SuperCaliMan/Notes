package com.supercaliman.note.ui.main.ViewModels


import androidx.lifecycle.*
import com.supercaliman.domain.Note
import com.supercaliman.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.domain.useCase.UpdateNoteTaskUseCase
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class DetailNoteViewModel(): ViewModel(),KoinComponent {


    private val deleteTaskModel:DeleteNoteTaskUseCase by inject()
    private val updateTaskModel:UpdateNoteTaskUseCase by inject()


    fun delete(uuid: String?){
        viewModelScope.launch {
            uuid?.let { deleteTaskModel.execute(uuid) }
        }
    }

    fun update(title:String,detail:String,uuid: String?){
        val date = Calendar.getInstance().time
        viewModelScope.launch {
            uuid?.let { updateTaskModel.execute(Note(uuid,title,detail,date)) }
        }
    }




}