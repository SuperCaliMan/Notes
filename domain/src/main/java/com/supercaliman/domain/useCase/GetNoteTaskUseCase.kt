package com.supercaliman.domain.useCase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import java.lang.Exception

class GetNoteTaskUseCase(private var repo:Repository) {



    suspend fun execute(): Result<List<Note>> = repo.getNotes()
}