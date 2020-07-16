package com.supercaliman.domain.useCase

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import java.lang.Exception

class DeleteNoteTaskUseCase(private var repo:Repository) {

    private val result = MediatorLiveData<Result<Boolean>>()



    suspend fun execute(uuid: String){
        result.postValue(Result.Loading)

        try {
            repo.deleteNote(uuid)
            result.postValue(Result.Success(true))
        }catch (e:Exception){
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<Boolean>> {
        return result
    }
}