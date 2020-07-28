package com.supercaliman.domain.useCase


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import javax.inject.Inject

class GetNoteTaskUseCase @Inject constructor(private var repo: Repository) {

    private val result = MediatorLiveData<Result<List<Note>>>()

    suspend fun execute() {
        result.postValue(Result.Loading)
        try {
            val res = repo.getNotes()
            result.postValue(Result.Success(res))
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<List<Note>>> {
        return result
    }
}