package com.supercaliman.domain.useCase

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import com.supercaliman.domain.UiNote
import timber.log.Timber
import java.lang.Exception

class CreateNoteTaskUseCase(private var repo:Repository){





    private val result = MediatorLiveData<Result<List<Note>>>()



    suspend fun execute(note:Note){
        result.postValue(Result.Loading)
        try {
            repo.createNote(note)
        }catch (e:Exception){
            Timber.w(e)
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<List<Note>>> {
        return result
    }
}