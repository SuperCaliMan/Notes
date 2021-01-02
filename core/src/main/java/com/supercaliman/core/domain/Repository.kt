package com.supercaliman.core.domain

import com.supercaliman.core.domain.dto.Note
import com.supercaliman.core.domain.dto.User
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getNotes(user: User): Flow<List<Note>>

    suspend fun createNote(note: Note)

    suspend fun deleteNote(uuid: String)

    suspend fun updateNote(note: Note)
}