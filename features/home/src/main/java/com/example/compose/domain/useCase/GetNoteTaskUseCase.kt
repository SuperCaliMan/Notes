package com.example.compose.domain.useCase


import androidx.lifecycle.MutableLiveData
import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.Result
import com.supercaliman.core.domain.dto.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNoteTaskUseCase @Inject constructor(
    private var repo: Repository,
) {

    private val result = MutableLiveData<Result<List<Note>>>()

    @InternalCoroutinesApi
    suspend fun getNotes() = withContext(Dispatchers.IO) {
        result.postValue(Result.Loading)
        try {
            val res = repo.getNotes()
            res.collect {
                result.postValue(Result.Success(
                    it.sortedBy { data -> data.date.time }
                        .reversed()))
            }
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    fun observe(): MutableLiveData<Result<List<Note>>> {
        return result
    }


}