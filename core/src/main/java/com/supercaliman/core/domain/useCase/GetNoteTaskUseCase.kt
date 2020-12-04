package com.supercaliman.core.domain.useCase


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.core.domain.Note
import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNoteTaskUseCase @Inject constructor(private var repo: Repository) {


    private val result = MediatorLiveData<Result<Flow<List<Note>>>>()
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

    fun observe(): MutableLiveData<Result<Flow<List<Note>>>> {
        return result
    }

    fun observeNote(): MutableLiveData<Result<Note>> {
        return resultDetailNote
    }
}