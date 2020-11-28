package com.supercaliman.core.domain.useCase

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.core.domain.Note
import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.Result
import javax.inject.Inject

class UpdateNoteTaskUseCase @Inject constructor(private var repo: Repository) {

    private val result = MediatorLiveData<Result<Boolean>>()


    suspend fun execute(note: Note) {
        result.postValue(Result.Loading)

        try {
            repo.updateNote(note)
            result.postValue(Result.Success(true))
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<Boolean>> {
        return result
    }
}