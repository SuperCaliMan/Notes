package com.supercaliman.domain

interface Repository {

    suspend fun getNotes(): List<Note>

    suspend fun createNote(note: Note)

    suspend fun deleteNote(uuid: String)

    suspend fun updateNote(note: Note)

    suspend fun getNote(uuid: String): Note?
}