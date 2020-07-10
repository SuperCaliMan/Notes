package com.supercaliman.domain.useCase

import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import java.lang.Exception

class CreateNoteTaskUseCase(private var repo:Repository){



    suspend fun execute(data:Note):Result<Boolean> = repo.createNote(data)
}