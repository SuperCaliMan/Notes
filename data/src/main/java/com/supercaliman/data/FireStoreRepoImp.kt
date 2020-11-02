package com.supercaliman.data

import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository

class FireStoreRepoImp(private var api:FireStoreAPI):Repository{

    override suspend fun getNotes(): List<Note> = api.getNotes()

    override suspend fun getNote(uuid: String): Note? = api.getNote(uuid)

    override suspend fun createNote(note: Note) {
        api.createNote(note)
    }

    override suspend fun deleteNote(uuid: String) {
        api.deleteNote(uuid)
    }

    override suspend fun updateNote(note: Note) {
        api.updateNote(note)
    }


}