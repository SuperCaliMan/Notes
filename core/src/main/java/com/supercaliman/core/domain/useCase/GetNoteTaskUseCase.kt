package com.supercaliman.core.domain.useCase


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.core.domain.Note
import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNoteTaskUseCase @Inject constructor(private var repo: Repository) {


    private val result = MediatorLiveData<Result<List<Note>>>()
    private val resultDetailNote = MediatorLiveData<Result<Note>>()


    @InternalCoroutinesApi
    suspend fun getNotes() = withContext(Dispatchers.IO) {
        result.postValue(Result.Loading)
        try {
            val res = repo.getNotes()
            res.collect {
                result.postValue(Result.Success(it))
            }
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