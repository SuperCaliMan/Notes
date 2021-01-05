package com.example.compose.domain.useCase

import androidx.lifecycle.MutableLiveData
import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.Result
import com.supercaliman.core.domain.dto.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateNoteTaskUseCase @Inject constructor(
    private var repo: Repository
) {

    private val result = MutableLiveData<Result<Boolean>>()

    suspend fun execute(note: Note) = withContext(Dispatchers.IO) {
        result.postValue(Result.Loading)
        try {
            repo.createNote(note)
            result.postValue(Result.Success(true))
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<Boolean>> {
        return result
    }
}