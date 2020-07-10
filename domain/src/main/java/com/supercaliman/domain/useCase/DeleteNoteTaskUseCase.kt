package com.supercaliman.domain.useCase

import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import java.lang.Exception

class DeleteNoteTaskUseCase(private var repo:Repository) {
    private var result:MutableLiveData<Result<Boolean>> = MutableLiveData<Result<Boolean>>()


    suspend fun execute(data: Note): MutableLiveData<Result<Boolean>> {
        try {
            val res = repo.deleteNote(data)
            result.postValue(res)
        }catch (e: Exception){
            result.postValue(Result.Error(e))
        }
        return result
    }
}