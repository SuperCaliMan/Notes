package com.supercaliman.domain.useCase


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNoteTaskUseCase @Inject constructor(private var repo: Repository) {

    private val result = MediatorLiveData<Result<List<Note>>>()
    private val resultDetailNote = MediatorLiveData<Result<Note>>()

    suspend fun getNotes() = withContext(Dispatchers.IO) {
        result.postValue(Result.Loading)
        try {
            val res = repo.getNotes()
            result.postValue(Result.Success(res))
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    suspend fun getNote(uuid: String) = withContext(Dispatchers.IO) {
        resultDetailNote.postValue(Result.Loading)
        try {
            val res = repo.getNote(uuid)
            res?.let {
                resultDetailNote.postValue(Result.Success(it))
            }
        } catch (e: java.lang.Exception) {
            resultDetailNote.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<List<Note>>> {
        return result
    }

    fun observeNote(): MutableLiveData<Result<Note>> {
        return resultDetailNote
    }
}