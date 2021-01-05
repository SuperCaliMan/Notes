package com.example.compose.domain.useCase

import androidx.lifecycle.MutableLiveData
import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteNoteTaskUseCase @Inject constructor(private var repo: Repository) {

    private val result = MutableLiveData<Result<Boolean>>()

    suspend fun execute(uuid: String) = withContext(Dispatchers.IO) {
        result.postValue(Result.Loading)

        try {
            repo.deleteNote(uuid)
            result.postValue(Result.Success(true))
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<Boolean>> {
        return result
    }
}