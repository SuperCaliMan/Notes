package com.supercaliman.domain

import java.sql.ResultSet

interface Repository {

    suspend fun getNotes():List<Note>

    suspend fun createNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}