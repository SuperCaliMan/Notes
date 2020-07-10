package com.supercaliman.domain

import java.sql.ResultSet

interface Repository {

    suspend fun getNotes():Result<List<Note>>

    suspend fun createNote(note: Note):Result<Boolean>

    suspend fun deleteNote(note: Note):Result<Boolean>

    suspend fun updateNote(note: Note):Result<Boolean>
}