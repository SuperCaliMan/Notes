package com.supercaliman.core.domain

import com.supercaliman.core.domain.dto.Note
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getNotes(): Flow<List<Note>>

    suspend fun createNote(note: Note)

    suspend fun deleteNote(uuid: String)

    suspend fun updateNote(note: Note)

//    fun setUser(user: User)
//
//    fun getUser(): User?
//
//    fun logout()
}