package com.supercaliman.domain.useCase

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import java.lang.Exception

class UpdateNoteTaskUseCase(private var repo:Repository) {

    private val result = MediatorLiveData<Result<Boolean>>()



    suspend fun execute(note:Note){
        result.postValue(Result.Loading)

        try {
            repo.updateNote(note)
            result.postValue(Result.Success(true))
        }catch (e:Exception){
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<Boolean>> {
        return result
    }
}