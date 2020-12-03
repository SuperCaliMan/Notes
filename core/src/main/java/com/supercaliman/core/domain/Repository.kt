package com.supercaliman.core.domain

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getNotes(): Flow<List<Note>>

    suspend fun createNote(note: Note)

    suspend fun deleteNote(uuid: String)

    suspend fun updateNote(note: Note)

    suspend fun getNote(uuid: String): Note?
}