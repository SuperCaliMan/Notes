package com.supercaliman.core.data

import com.google.firebase.firestore.DocumentReference
import com.supercaliman.core.domain.dto.Note
import com.supercaliman.core.domain.dto.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface NotesApi {
    @ExperimentalCoroutinesApi
    @Throws(Exception::class)
    suspend fun getNotes(user: User): Flow<List<Note>>

    @Throws(Exception::class)
    suspend fun deleteNote(uuid: String): Void?

    @Throws(Exception::class)
    suspend fun createNote(note: Note): DocumentReference?

    @Throws(Exception::class)
    suspend fun updateNote(note: Note): Void?


}